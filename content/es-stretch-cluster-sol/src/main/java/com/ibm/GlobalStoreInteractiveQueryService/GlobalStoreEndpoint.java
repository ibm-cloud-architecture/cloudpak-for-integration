package com.ibm.GlobalStoreInteractiveQueryService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;


@ApplicationScoped
@Path("/global-store")
public class GlobalStoreEndpoint {

    @Inject
    GlobalStoreInteractiveQuery interactiveQueries;

    private static final Logger LOG = Logger.getLogger(GlobalStoreEndpoint.class);

    @GET
    @Path("data/{user_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGlobalStoreData(@PathParam("user_id") String user_id) {
        FinancialMessageDataResult result = interactiveQueries.getFinancialMessageData(user_id);

        LOG.info("Result -  " + result);
        

        if (result.getResult().isPresent()) {         
            LOG.info("Result is present -  " + result);            
            return Response.ok(result.getResult().get()).build();
        }
        else if (result.getHost().isPresent()) {                  
            URI otherUri = getOtherUri(result.getHost().get(), result.getPort().getAsInt(),
                    user_id);
            return Response.seeOther(otherUri).build();
        }
        else {                                                    
            return Response.status(Status.NOT_FOUND.getStatusCode(),
                    "No data found from global store for " + user_id).build();
        }


        // if (result.getHost().isPresent()) {
        //     URI otherUri = getOtherUri(result.getHost().get(), result.getPort().getAsInt(),
        //             user_id);
        //     return Response.seeOther(otherUri).build();
        // }
        // else {
        //     return Response.status(Status.NOT_FOUND.getStatusCode(),
        //             "No data found from global store for " + user_id).build();
        // }
    }

    @GET
    @Path("meta-data")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PipelineMetadata> getMetaData() {                 
        return interactiveQueries.getMetaData();
    }

    private URI getOtherUri(String host, int port, String user_id) {
        try {
            return new URI("http://" + host + ":" + port + "/global-store/data/" + user_id);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
