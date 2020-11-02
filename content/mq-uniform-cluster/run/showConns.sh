#!/bin/bash
clear='printf "\033c"'
$clear

green='\033[0;32m'
lgreen='\033[1;32m'
nc='\033[0m'

APPNAME=${2:-amqsghac}
DELAY=${3:-1s}

for (( i=0; i<100000; ++i)); do
  CONNCOUNT=`echo "dis conn(*) where(appltag eq '$APPNAME')" | runmqsc -c $1 | grep "  CONN" | wc -w`
  BALANCED=`echo "dis apstatus('$APPNAME')" | runmqsc $1 | grep "  BALANCED"`
  $clear
  echo -e "${green}$1${nc} / ${green}$APPNAME${nc} -- ${lgreen}$CONNCOUNT${nc}"
  echo "dis conn(*) where(appltag eq '$APPNAME')" | runmqsc $1 | grep "  CONN"
  sleep $DELAY
done




