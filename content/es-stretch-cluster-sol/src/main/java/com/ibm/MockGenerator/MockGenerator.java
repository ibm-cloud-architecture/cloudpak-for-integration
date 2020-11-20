package com.ibm.MockGenerator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.ibm.Topology.GlobalStoreTopology;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.scheduler.Scheduled;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.*;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;
import java.util.Random;

import com.ibm.Model.FinancialMessage;
import org.jboss.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

@ApplicationScoped
public class MockGenerator{

    @ConfigProperty(name = "HOSTNAME")
    String host;

    private Random random = new Random();
    private String[] sampleStockSymbols = {"AXP", "BABA", "CAT"};

    private FinancialMessage mock = new FinancialMessage("1234", "AXP", "SWISS", "bonds", "10/20/2019",
                                                         "10/21/2019", 12, 1822.38, 21868.55, 94, 7,
                                                         true, false, false, false, false);
    private KafkaRecord<String, FinancialMessage> temp;
    private static final Logger LOG = Logger.getLogger(GlobalStoreTopology.class);

    @Outgoing("mock-messages")
    public Flowable<KafkaRecord<String, FinancialMessage>> produceMock() {
        return Flowable.interval(5, TimeUnit.SECONDS)
                       .map(tick -> {
                           KafkaRecord<String, FinancialMessage> temp = generateRandomMessage(mock);
                           GlobalStateLookUp(temp);
                           return temp;
                        });
    }


    @Inject
    @Channel("reproduce-mock-messages")
    private Emitter< FinancialMessage> reproduceMock;

    @Inject
    @Channel("failure-messages")
    private Emitter<FinancialMessage> produceFailure;

    public void reproduceRecord (FinancialMessage record ){
        LOG.infov(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> REPRODUCING ... " + record.toString() );
        reproduceMock.send( KafkaRecord.of(record.user_id, record)  );
    }

    public void GlobalStateLookUp ( KafkaRecord<String, FinancialMessage> record ) throws IOException {
        String parsedresponse = curlRequest(record.getKey());
        int retries = 0;

        try {Thread.sleep(5000);}
        catch (InterruptedException e) {e.printStackTrace();}

        if (parsedresponse.equalsIgnoreCase(record.getKey())){
            LOG.infov(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> MATCHED at FIRST ATTEMPT  PARSED ID: " + parsedresponse + " Key: "+ record.getKey());
        }
        while(retries<5){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            parsedresponse = curlRequest(record.getKey());
            LOG.infov(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> RETRY # " + retries +  " Parsed ID: "+parsedresponse + " Key: "+ record.getKey() );
            if (parsedresponse.equalsIgnoreCase(record.getKey())) {
                LOG.infov(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> PARSED MATCHED; PARSED ID: " + parsedresponse + " Key: "+ record.getKey());
                break;
                }
            else {
                retries = retries + 1;
                reproduceRecord(record.getPayload());
            }

        }
        if ( retries == 5 ){
            LOG.infov(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> PRODUCRING TO FAILURE TOPIC ... " + record.getPayload().toString() );
            produceFailure.send(KafkaRecord.of(record.getPayload().user_id, record.getPayload()));
        }
    }

    public String curlRequest (String key) {
        String parsedresponse = "null";
        String stringUrl = "http://"+ host + ":8080/global-store/data/" + key;
        LOG.infov(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> URL " + stringUrl);
        try {
            URL url = new URL(stringUrl);
            String line = null;
            URLConnection conn = url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            LOG.infov(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> CURL REQUEST WAS MADE");
            while ((line = br.readLine()) != null) {
                LOG.infov(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> CURL LINE by LINE "+ line);
                JSONObject obj = new JSONObject(line);
                parsedresponse = obj.getString("user_id");
                return parsedresponse;
            }
        } catch (IOException e) {
            //e.printStackTrace();
            return parsedresponse;
        }
        return parsedresponse;
    }


    public KafkaRecord<String, FinancialMessage> generateRandomMessage(FinancialMessage mock) {

        mock.user_id = String.valueOf(random.nextInt(100));
        mock.stock_symbol = sampleStockSymbols[random.nextInt(3)];

        return KafkaRecord.of(mock.user_id, mock);
    }
}
