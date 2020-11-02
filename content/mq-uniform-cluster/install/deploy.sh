#!/bin/bash

oc apply -f ./install/secret-tls.yaml --dry-run

oc apply -f ./install/cm-ini-qm1.yaml --dry-run
oc apply -f ./install/cm-ini-qm2.yaml --dry-run
oc apply -f ./install/cm-ini-qm3.yaml --dry-run

oc apply -f ./install/cm-mqsc-qm1.yaml --dry-run
oc apply -f ./install/cm-mqsc-qm2.yaml --dry-run
oc apply -f ./install/cm-mqsc-qm3.yaml --dry-run

oc apply -f ./install/qm-qm1.yaml --dry-run
oc apply -f ./install/qm-qm2.yaml --dry-run
oc apply -f ./install/qm-qm3.yaml --dry-run