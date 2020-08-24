The MQ HA C Sample program `amqsphac` does not send Persistent Messages to MQ this is because the src code set it to non persistent by default:

```
md.Persistence = MQPER_NOT_PERSISTENT;
```
I have modified the src code for this sample program like so.

```
/* Explicitly set Persitence to the default value for clarity */
   md.Persistence = MQPER_PERSISTENCE_AS_Q_DEF;
```

You can use the program locally by calling it and passing in the same paramers

`amqsphac_persist IN.PERSIST hasingle`

If you are having trouble runnng locally try using it in Docker

```
docker run --rm -e LICENSE=accept \
       --volume ~/full-path-parent-dir/mq-sample-programs:/mnt/usr/run \
       --detach \
        ibmcom/mq:latest
```

`docker exec -ti <id of container> bash`

`/mnt/usr/run/amqsphac_persist IN.PERSIST hasingle`