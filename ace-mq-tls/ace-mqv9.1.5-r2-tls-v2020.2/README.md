# ACE v11.0.0.9 and MQ v9.1.5-r2 (TLS) Connection on CloudPak for Integration v2020.2

# Setup MQ

## Create MQ certificates: 
- Follow this guide for [Creating Certificates and KDB files](<./Create KDB.md>)
- Follow this video to see how to import the certs into TLS Secrets and deploy them with MQ: [How to configure MQ with TLS certificates as secrets and expose traffic externally with CP4I](https://www.youtube.com/watch?v=R0RZEL4jiUI)

## Deploy MQ `QueueManager` with TLS Secrets

- You can follow the video above or use the sample yaml syntax for deploying MQ with TLS Secrets through the `pki.keys` and `pki.trust`  section:
    -  `QUICKSTART` QM [quickstart-cp4i](<./quickstart-cp4i.yaml>)
    -  `hasingle` QM [mq-dev-ha-single](<./mq-dev-ha-single.yaml>)

## Create SNI Channel name based Route

- [Knowledge Center](https://www.ibm.com/support/knowledgecenter/SSFKSJ_9.2.0/com.ibm.mq.ctr.doc/cc_conn_qm_openshift.htm)
- [MQ Channel SNI mapping rules](https://www.ibm.com/support/pages/ibm-websphere-mq-how-does-mq-provide-multiple-certificates-certlabl-capability)
- You can follow the video above or use the sample yaml
    - Route for `QUICKSTART` QM [quickstart-ext-conn-traffic](<./quickstart-ext-conn-traffic.yaml>)
    - Route for `hasingle` QM [mq-dev-ha-single-conn-traffic](<./mq-dev-ha-single-conn-traffic.yaml>)




# External Cluster Access for MQ

[ACE configuration on CP4I](https://www.youtube.com/watch?v=4zYoLk1drtY)

ACE properties
<ul>
<li> Queue Name: IN.PERSIST </li>
<li> Connection: MQ client connection properties </li>
<li> Destination queue manager name: hasingle </li>
<li> Queue manager host name: mq-dev-ha-single-ibm-mq-qm-cp4i.apps.ctocp44.ocp.csplab.local (newly-created-route) </li>
<li> Listener port number: 443 </li>
<li> Channel name: HASINGLE.CONN </li>
<li> SSL: True </li>
<li> SSL cipher specification: ANY_TLS12 </li> 
</ul>

# Internal Cluster Access for MQ

[ACE configuration on CP4I](https://www.youtube.com/watch?v=4zYoLk1drtY)

If App Connect Dashboard and MQ are deployed on the same cluster then you can use service name to establish connection between them
Here are the ACE properties: <br/>
<ul>
<li> Queue Name: IN.PERSIST </li>
<li> Connection: MQ client connection properties </li>
<li> Destination queue manager name: hasingle </li>
<li> Queue manager host name: ha-single-ibm-mq (queue-manager-service-name) </li>
<li> Listener port number: 1414 </li>
<li> Channel name: HASINGLE.CONN </li>
<li> SSL: True </li>
<li> SSL cipher specification: ANY_TLS12 </li> 
</ul>

### Important:
 * **You DO NOT need additional SNI mapping for service name with port 1414**
 * If App Connect and MQ are on the same cluster but different namespaces then you have to provide \<queue-manager-service-name\>.\<your-namespace\>.svc

# Different users in MQ
johndoe vs aceuser

In MQ Versions 9.1.5+ it is no longer recommend to create and authorize Operating System Users on the MQ Container. Either setup `LDAP` for Client Connections or use SSL Cert Subject DN Matching. 

For non production environments you can relax security checking for MQ Client Connections by Adding a Channel Authentication Record blocking the `nobody` User and with the addition of environment variables.

In some of the MQ videos we walkthrough the process of authorizing the `johndoe` User.

This is an extra step when deploying MQ using environment variable `MQS_PERMIT_UNKNOWN_ID`. That variable plus the `CHLAUTH` Block User rule allows Client Connections to MQ as non Operating System user as if you were an OS user. It is a way of spoofing a User that is not real.

```
  template:
    pod:
      containers:
        - name: qmgr
          env:
            - name: MQS_PERMIT_UNKNOWN_ID
              value: 'true'
```              

In later videos ([How to deploy ephemeral QM with MQSNOAUT in Cloud Pak for Integration](https://www.youtube.com/watch?v=9XKJvadu_wQ)) we changed the environment variable to `MQSNOAUT` this var plus the `CHLAUTH` Block User rule is an easier way of disabling MQ Security checks for Client Connections and doesn't require authorizing specific real (or fake) Users, like  `johndoe` or `aceuser` .
```
  template:
    pod:
      containers:
        - name: qmgr
          env:
            - name: MQSNOAUT
              value: 'yes'
```
