### Table of Content

- [Table of Content](#table-of-content)
- [General Links](#general-links)
  - [Cloud Manager](#cloud-manager)
  - [API Manager](#api-manager)
  - [Other Useful References](#other-useful-references)
- [OIDC Delegation	(Third Party OAuth)](#oidc-delegationthird-party-oauth)
- [SSO on Dev Portal](#sso-on-dev-portal)
- [SSO on Admin GUI](#sso-on-admin-gui)
- [Host via SSL](#host-via-ssl)
- [Host public and private APIs](#host-public-and-private-apis)
- [Infrastructure-as-Code Tooling](#infrastructure-as-code-tooling)
- [Ability to throttle](#ability-to-throttle)
- [Update OpenAPI docs via CLI](#update-openapi-docs-via-cli)
- [TLS trust](#tls-trust)
- [Test caching](#test-caching)
- [Ability to aggregate requests](#ability-to-aggregate-requests)
- [Plugin architecture sample](#plugin-architecture-sample)
- [Test upstream load balancing](#test-upstream-load-balancing)



### General Links

#### Cloud Manager
- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.cmc.doc/tutorial_creating_porg.html

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.cmc.doc/rapic_cmc_checklist.html

#### API Manager

Setup

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.apionprem.doc/tapim_management_tutorials.html

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.apionprem.doc/rapic_apim_checklist.html


Tutorial: Creating a proxy REST API definition

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.apionprem.doc/tutorial_apionprem_apiproxy.html


#### Other Useful References

Setup/Configure/Run Tutorials

- https://www.ibm.com/support/knowledgecenter/en/SSMNED_v10/com.ibm.apic.tutorials.doc/tutorials_home.html

Working with API Definitions - Built-in policies

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.toolkit.doc/rapim_ref_ootb_policies.html

API Connect context variables

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.toolkit.doc/rapim_context_var.html

End-to-end solution example

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.overview.doc/overview_apimgmt_about.html

Product Lifecycle

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.apionprem.doc/capim_product_lifecycle.html


### OIDC Delegation	(Third Party OAuth)

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.apionprem.doc/oauth_introspection.html

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.cmc.doc/tapic_cmc_oauth_thirdparty.html


### SSO on Dev Portal	

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.cmc.doc/capic_registry_create_oidc_cmc.html


### SSO on Admin GUI

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.cmc.doc/capic_registry_create_oidc_cmc.html


### Host via SSL

HTTPS is enabled automatically for all APIs

- https://www.ibm.com/mysupport/s/question/0D50z000062kxaECAQ/tls-mutual-authentication-in-api-connect-for-individual-api?language=en_US

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.cmc.doc/capic_cmc_tlsprofiles_concepts.html

### Host public and private APIs

Public vs private can be the difference between having security definition

- https://www.ibm.com/support/knowledgecenter/en/SSMNED_v10/com.ibm.apic.toolkit.doc/tapim_sec_api_config.html

### Infrastructure-as-Code Tooling	

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.toolkit.doc/capim-toolkit-cli-overiew.html
- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.toolkit.doc/capim-toolkit-cli-scripts.html
- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.cliref.doc/apic.html
  
REST API:
- https://apic-api.apiconnect.ibmcloud.com/v10/
- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.reference.doc/rapic_rest_apis.html

Examples:
- https://github.com/ibm-apiconnect/example-toolkit-scripts
- https://chrisphillips-cminion.github.io/apiconnect/2019/09/26/APIConnect-PublishAPIRestStaging.html

### Ability to throttle	

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.overview.doc/overview_concepts_ratelimit.html

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.toolkit.doc/rapim_ref_ootb_policyratelimit.html


### Update OpenAPI docs via CLI	

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.toolkit.doc/capim-toolkit-cli-getting-started.html

Examples:
- https://github.com/ibm-apiconnect/example-toolkit-scripts
- https://chrisphillips-cminion.github.io/apiconnect/2019/09/26/APIConnect-PublishAPIRestStaging.html

### TLS trust	

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.cmc.doc/capic_cmc_tlsprofiles_concepts.html


### Test caching	

Invoke with `Time to Live` set

- https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.toolkit.doc/rapim_ref_ootb_policyinvoke.html


### Ability to aggregate requests

Aggregation can be done with multiple `invoke` policies, and mapping the payloads together.

- https://developer.ibm.com/recipes/tutorials/api-connect-aggregating-apis-mapping-sample/


### Plugin architecture sample

TODO: assembly + policies + custom policies

### Test upstream load balancing

Potential: Load balancer group in DataPower

- https://www.ibm.com/support/knowledgecenter/en/SS9H2Y_10.0/com.ibm.dp.doc/lbg_loadbalancergroup.html

