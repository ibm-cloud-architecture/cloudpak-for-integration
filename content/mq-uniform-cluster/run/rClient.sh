#!/bin/bash

CCDT_NAME=${2:-"*QM_GROUP"}

for (( i=0; i<$1; ++i)); do
  echo "Starting amqsghac" $CCDT_NAME
  /opt/mqm/samp/bin/amqsghac APPQ $CCDT_NAME &
done
