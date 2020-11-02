#!/bin/bash

oc delete qmgr mq-uni-clus1 -n cp4i
oc delete route mq-uni-clus1-conn-traffic -n cp4i
oc delete pvc data-mq-uni-clus1-ibm-mq-0 -n cp4i
oc delete cm qm1-mqsc-cm -n cp4i
oc delete cm qm1-ini-cm -n cp4i

oc delete qmgr mq-uni-clus2 -n cp4i
oc delete route mq-uni-clus2-conn-traffic -n cp4i
oc delete pvc data-mq-uni-clus2-ibm-mq-0 -n cp4i
oc delete cm qm2-mqsc-cm -n cp4i
oc delete cm qm2-ini-cm -n cp4i

oc delete qmgr mq-uni-clus3 -n cp4i
oc delete route mq-uni-clus3-conn-traffic -n cp4i
oc delete pvc data-mq-uni-clus3-ibm-mq-0 -n cp4i
oc delete cm qm3-mqsc-cm -n cp4i
oc delete cm qm3-ini-cm -n cp4i
