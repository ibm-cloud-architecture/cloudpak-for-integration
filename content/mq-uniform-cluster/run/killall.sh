#!/bin/bash

APPNAME=${1:-amqsghac}

kill $(ps -e | grep $APPNAME | awk '{print $1}')


