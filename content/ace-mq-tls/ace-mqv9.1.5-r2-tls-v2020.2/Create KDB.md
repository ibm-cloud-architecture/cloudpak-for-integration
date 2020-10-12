# Step 1: Create self signed certs for server and client

1. Create a directory to hold your new certificates and keys then cd into it:
   > `cd /Users/danielorlan/tutorials/mq/ssl`
1. Create self signed certs for the server
    > `openssl req -newkey rsa:2048 -nodes -keyout server.key -x509 -days 365 -out server.crt`

    You will be asked to fill in the DN next I used these values:
    > 
    ```    
    Country Name (2 letter code) []:US
    State or Province Name (full name) []:NY
    Locality Name (eg, city) []:NY
    Organization Name (eg, company) []:IBM
    Organizational Unit Name (eg, section) []:CPAT
    Common Name (eg, fully qualified host name) []:com.ibm.com
    Email Address []:daniel.orlan@ibm.com
    ```
2. Create self signed cert for the client

    > `openssl req -newkey rsa:2048 -nodes -keyout client.key -x509 -days 365 -out client.crt`

    You will be asked to fill in the DN next I used these values:
    > 
    ```    
    Country Name (2 letter code) []:US
    State or Province Name (full name) []:NY
    Locality Name (eg, city) []:NY
    Organization Name (eg, company) []:IBM
    Organizational Unit Name (eg, section) []:CPAT
    Common Name (eg, fully qualified host name) []:com.ibm.com
    Email Address []:daniel.orlan@ibm.com
    ```
 3. Your directory should now have 4 files:
    1. `client.crt` (the clients **public certificate**)
    2. `client.key`	(the clients **private key**)
    3. `server.crt`	(the servers **public certificate**)
    4. `server.key` (the servers **private key**)

# Step 2: Create a p12 file for the client

1. Use the clients private key and public cert to create a pkcs12 file

    > `openssl pkcs12 -export -in client.crt -inkey client.key -out client.p12 -name "client pkcs12"`

    Enter password: **passw0rd** when prompted

2. Use these commands to verify the p12 looks good:
    > `keytool -list -v -keystore client.p12 -storepass passw0rd`

3. View the certificate:
    > `openssl pkcs12 -info -in client.p12`

4. View the private key:
    > `openssl pkcs12 -in client.p12 -nocerts -nodes`
5. Your directory should now have an additional file `client.p12`

# Step 3: Create the KDB file

1. Run MQ in a docker Image:
    ``` 
    docker run --rm -e LICENSE=accept \
        --volume /Users/danielorlan/tutorials/mq/ssl:/mnt/certs \
        --detach \
        ibmcom/mq:latest
    ```

- `--volume /Users/danielorlan/tutorials/mq/ssl:/mnt/certs` the directory we created before which contains all keys and certs will be mounted inside the container to `mnt/certs`


- Get the GUID for the running container and exec into it: 

    > `docker exec -it 12f \bin\bash`

1. Create the kdb file named: `mq-secure.kdb` with password: `passw0rd`
    > `runmqakm -keydb -create -db mq-secure.kdb -pw passw0rd -type cms -expire 1000 -stash`

2. Import the server's public certificate into the client key database:

    > `runmqakm -cert -add -label mqserver -db mq-secure.kdb -pw passw0rd -trust enable -file server.crt`
    
3. Import the client's p12 file into the client key database and label it `ibmwebspheremquser` :

    > `runmqckm -cert -import -file client.p12 -pw passw0rd -type pkcs12 -target mq-secure.kdb -target_pw passw0rd -target_type cms -label "client pkcs12" -new_label ibmwebspheremquser` 

4. View the contentes of the kdb file
    > `runmqakm -cert -list all -db mq-secure.kdb -stashed`

    > `runmqakm -cert -details -db mq-secure.kdb -stashed -label mqserver`

    > `runmqakm -cert -details -db mq-secure.kdb -stashed -label ibmwebspheremquser`
5. Provide these files to MQ C Client to enable SSL mutual authentication
    1) `mq-secure.kdb`  (the key databse file)    
    2) `mq-secure.sth` (the stash file containing the password for the kdb)
