### APIC CLI Cheatsheet

This is intended to be a cheatsheet and quick reference for working with the APIC CLI tool. Always refer to the knowledge center if more details are needed 

https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.toolkit.doc/tapim_cli_install.html

https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.toolkit.doc/capim-toolkit-cli-overiew.html

*Table of Contents*
- [APIC CLI Cheatsheet](#apic-cli-cheatsheet)
- [Scripting Commands](#scripting-commands)
- [Login](#login)
  - [Realm](#realm)
  - [API Manager](#api-manager)
  - [Cloud Manager](#cloud-manager)
  - [SSO (OIDC Registry Types)](#sso-oidc-registry-types)
- [Working with APIs](#working-with-apis)
- [Creating Draft API](#creating-draft-api)
- [Creating Draft Product and Publishing](#creating-draft-product-and-publishing)
- [Replacing the Product](#replacing-the-product)
- [Create Another New API/Product](#create-another-new-apiproduct)


### Scripting Commands
First run before running commands with `apic` CLI for the first time.

```
apic --accept-license
```

Gather your URLs and other often referenced values and set them as variables for convenience.
```
api_mgmt='manager.apic-v10.ibm.com'
cloud_mgmt='admin.apic-v10.ibm.com'
catalog='sandbox'
org='shellorg'

username=<APIM_USERNAME>
password=<APIM_PASSWORD>
```


### Login

https://www.ibm.com/support/knowledgecenter/SSMNED_v10/com.ibm.apic.toolkit.doc/rapic_cli_login.html#rapic_cli_login__determine_idp

#### Realm
* Realm for API Manager local user registry: `provider/default-idp-2`
* Realm for Cloud Manager local user registry: `admin/default-idp-1`

`apic client-creds:set ./eks3node-creds.json`

#### API Manager

Manual Login with Local User

`apic login --server $api_mgmt --username $username --password $password --realm provider/default-idp-2`


#### Cloud Manager

Realm for local user registry: `admin/default-idp-1`

```
cm_username=<CMGR_USERNAME>
cm_password=<CMGR_PASSWORD>
apic login --server $cloud_mgmt --username $cm_username --$cm_password --realm admin/default-idp-1
```

#### SSO (OIDC Registry Types)

```
apic login --server $api_mgmt --sso
```
When prompted for `Context?`, type `provider`. Then open the browser and login with the OIDC user account. Paste in API key in the terminal after successful login in the browser.

Note: if you don't get the API key screen after logging in and instead APIC looks like it logs you in regularly, try a different browswer or clearly your cache.

### Working with APIs

### Creating Draft API

Get List of APIs in Sandbox catalog

`apic apis:list-all --scope catalog --server $api_mgmt --catalog $catalog --org $org`


Create a draft of an API. This will create the API in API Manager but it is not associated with any products yet.

```
apic draft-apis:create --server $api_mgmt --org $org example-yamls/find-branch-api-1.0.0.yaml
```

### Creating Draft Product and Publishing
Get List of Products

```
apic products:list-all --server $api_mgmt --org $org --catalog $catalog --scope catalog
```


Create a draft of a product containing the above API

```
apic draft-products:create --server $api_mgmt --org $org example-yamls/branch-product-1.0.0.yaml
```


Publish the draft product above. The CLI `product:publish` requires the `$ref` to the API yaml in order
to publish the product (but not for draft)

```
apic products:publish --server $api_mgmt --catalog $catalog --org $org example-yamls/branch-product-1.0.0.yaml
```


### Replacing the Product

Now a product containing the `1.0.0` of the API is published. Note that the 1.0.0 find branch API is pointing to an improper backend URL. So let's say we've created find branch 2.0.0 to update the backend URL so that it works properly, and we'll replace the product with the new version, so subscriptions will migrate automatically.


We first create draft of the new version API and create a new draft a product that references the new API.

```
# Create a new draft API
apic draft-apis:create --server $api_mgmt --org $org example-yamls/find-branch-api-2.0.0.yaml

# Create a new draft product
apic draft-products:create --server $api_mgmt --org $org example-yamls/branch-product-2.0.0.yaml
```

```
# Grab the previously published product (in this case we're interested in find-branch-product:1.0.0)
product_url=$(apic products:list-all --server $api_mgmt --org $org --catalog $catalog --scope catalog | grep 'find-branch-product:1.0.0.*published' | awk '{print $4}')

product_url="product_url: ${product_url}"

echo $product_url
```

Generate product mapping file ([docs here for product mapping file format](https://www.ibm.com/support/knowledgecenter/en/SSMNED_v10/com.ibm.apic.toolkit.doc/capim-toolkit-cli-manage-products.html)).

```
echo ${product_url}>product-map.txt
echo "plans:">>product-map.txt
echo "- source: default">>product-map.txt
echo "  target: default">>product-map.txt
```

Create the new draft product, stage it, and then use it to replace the old one. 

Note the product given in this command is the name of the product that will replace the old product. The product identified in the mapping file is the product being replaced.

```
# Stage new product
apic products:publish --server $api_mgmt --catalog $catalog --org $org --stage example-yamls/branch-product-2.0.0.yaml

# Replace published 1.0.0 product with staged 2.0.0 product
apic products:replace --server $api_mgmt --org $org --scope catalog --catalog sandbox find-branch-product:2.0.0 product-map.txt
```


### Create Another New API/Product

```
apic draft-apis:create --server $api_mgmt --org $org example-yamls/find-branch-api-3.0.0.yaml
apic draft-products:create --server $api_mgmt --org $org example-yamls/branch-product-3.0.0.yaml
apic products:publish --server $api_mgmt --catalog $catalog --org $org example-yamls/branch-product-3.0.0.yaml
```

<!--
TODO 
- add a product 3.0 as separate product, without replacing the 2.0.0 product
-->
