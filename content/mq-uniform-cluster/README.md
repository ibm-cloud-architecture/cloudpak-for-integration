# Deploy an IBM MQ Uniform Cluster

1. Install the componentes to your openshift cluster
     
       chmod +x ./install.deploy.sh && ./install/deploy.sh
2. Run MQ in Docker on your Local Machine

    ```
    docker run --rm -e LICENSE=accept \
    --volume $(pwd):/mnt/usr/ \
    -e MQCCDTURL=file:///mnt/usr/config/ccdt.json \
    -e MQSSLKEYR=/mnt/usr/ssl/mq-secure \
    --detach \
    ibmcom/mq
    ```
3. Open three terminals side by side and execute these commands respectivly
    - `docker exec -ti 2fb ./mnt/usr/run/showConns.sh qm1`
    - `docker exec -ti 2fb ./mnt/usr/run/showConns.sh qm2`
    - `docker exec -ti 2fb ./mnt/usr/run/showConns.sh qm3`

    You should initially seee `qm1 / amqsghac -- 0` in these terminals

4. Open a fourth terminal and run this command to create 9 connections
    -   `docker exec -ti 2fb ./mnt/usr/run/rClient.sh 9`
    
    Watch the `showConns` terminals as the Connections get balanced evenly 3 to each QM
5. Cleanup, run `./install/cleanup.sh` to remove the Uniform Cluster and resources.

---
These instructions have been modified from [Callum Jackson's Tutorial](https://github.ibm.com/CALLUMJ/MQonCP4I/blob/master/instructions/uniformCluster/instructions.md#verifying-the-uniform-cluster-works)