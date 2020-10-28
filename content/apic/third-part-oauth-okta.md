1. Go to Okta, create an developer account - https://developer.okta.com/signup/

2. Create an `Application` (type `Web` if authorization code grant flow is desired, or `Native` if password grant flow is desired)

3. Client ID and Client Secret will be used to call APIC secured API as well as the initial token request

4. Request OIDC Access token from Okta (password grant type):

    ```
    okta_user_id='<your-okta-user>'
    okta_password='<your-okta-pwd>'

    okta_client_id='<okta-client-id>'
    okta_client_secret='<okta-client-secret>'

    base64_auth=$(echo -n "$okta_client_id:$okta_client_secret" | base64)

    curl --request POST \
      --url https://dev-154953.okta.com/oauth2/default/v1/token \
      --header 'accept: application/json' \
      --header "authorization: Basic $base64_auth" \
      --header 'content-type: application/x-www-form-urlencoded' \
      --data "grant_type=password&username=$okta_user_id&password=$okta_password&scope=openid"
    ```
    
    Returns:
    
    ```
    {
      "token_type": "Bearer",
      "expires_in": 3600,
      "access_token": "eyJraWQiOiJGTktqeDVDZlFmSXVpenFucHlRUmRzWElpSGlYaWVVcnBOZ0IyUVBiTW1RIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULnNaaHFQR08zMjF4OG82NEMxUy1SMkNJWmY1Nko3T2l3VVptbjQyN3g1ZUkiLCJpc3MiOiJodHRwczovL2Rldi0xNTQ5NTMub2t0YS5jb20vb2F1dGgyL2RlZmF1bHQiLCJhdWQiOiJhcGk6Ly9kZWZhdWx0IiwiaWF0IjoxNjAzMjIxODEzLCJleHAiOjE2MDMyMjU0MTMsImNpZCI6IjBvYTE3bXpyYzVobDZuaW94NHg3IiwidWlkIjoiMDB1M2E1bDBodHJieDlKQ3A0eDYiLCJzY3AiOlsib3BlbmlkIl0sInN1YiI6ImMucy50c3VpODMxQGdtYWlsLmNvbSJ9.c_xIViS17D8WMexBfDxrVndPqGmQJ1w-cB9pY1hChEvJ_n-LWgzY0PtNRtlQCe42igZp9idynR44vKkempAMHyASiB6x9TkwpjmVbao0VgfGmtf7MA3kIEa-xeUXKe6SE089wGoLr5L83OuruIW1JR5MKxiMi_QrbclQ5Z3Rg-5MZeo3Cvu-Alc4andB1jqC1Bi0u39JidoDjpkDoHkGJr17v-NBYnJToN84NM5WwkevxuqO_BF29Y154rXUykvbumwNbV8t27ve_hCbmEHCsqpx65L7VOimk224hxGn2tDYo4uYJHU76sXql5hH2xdZOsFCa4kOO6HVGHRQxdS0xQ",
      "scope": "openid",
      "id_token": "eyJraWQiOiJGTktqeDVDZlFmSXVpenFucHlRUmRzWElpSGlYaWVVcnBOZ0IyUVBiTW1RIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiIwMHUzYTVsMGh0cmJ4OUpDcDR4NiIsInZlciI6MSwiaXNzIjoiaHR0cHM6Ly9kZXYtMTU0OTUzLm9rdGEuY29tL29hdXRoMi9kZWZhdWx0IiwiYXVkIjoiMG9hMTdtenJjNWhsNm5pb3g0eDciLCJpYXQiOjE2MDMyMjE4MTMsImV4cCI6MTYwMzIyNTQxMywianRpIjoiSUQuMjdTTUpaVjJYLWVvYmhCR3VvaEpjTUhVMWxZY1BNaTBRZ0FLd2dHU0t5byIsImFtciI6WyJwd2QiXSwiaWRwIjoiMDBvM2E1a3g4Y1dpaDVsR0w0eDYiLCJhdXRoX3RpbWUiOjE2MDMyMjE4MTMsImF0X2hhc2giOiI1dWZtb2lUMXpETEdwU2R5TzhHNDdRIn0.AuZKCOGzw7b5_9OnAdBzPuSwj3uutC4BkBGqUDMxZHQgh-Ss216_xh91CHfjZZkP-89Tc8E7pwPTBJrG3UhDoeykt_uPd7H7_mUmiYsJ9lrogJbTofS1yGOMm8WU9fOW1WgLNj_do_q6FNoIMBBzTFFI-XkYh-8TdHdz8ZfZg2hV_s_bnKEDqyvOYyyklmM2kvgd54mUHWhobfqMjf2wNPnBmMjVPkG4WdK3tQx4gvHfhGuh0EDj5GNVSWdEbPcbKIxAddDOPUrdD5F6glpfUSyhVlmYwrXHBrl8eNpvrPvO5byooo_Zi2nj6XQJUAZRc4iIN0Lb1zVZ_MIzjVERSA"
    }
    ```
    
5. Calling a Third Party Oauth protected API. `X-IBM-Client-Id` is from the API subscription or the sandbox for testing. The header can removed if the `cliendId` security definition is not set to the API.

    ```
    okta_access_token=<extract-from-previous-response>
    
    curl -k --location --request GET 'https://rgw.apic-eks-3node.ibm-cpat.com/first-org-local-owned/test-catalog/branches' \
      --header 'X-IBM-Client-Id: 5cee3c6da356d5a97c609902e70f1593' \
      --header "x-introspect-basic-authorization-header: $okta_client_id:$okta_client_secret" \
      --header 'scope: openid' \
      --header "authorization: Bearer $okta_access_token"
    ```
    
    Returns:

    ```
    [{"id": "0b3a8cf0-7e78-11e5-8059-a1020f32cce5","type": "atm","address": {"street1": "600 Anton Blvd.","street2": "Floor 5","city": "Costa Mesa","state": "CA","zip_code": "92626"}},{"id": "9d72ece0-7e7b-11e5-9038-55f9f9c08c06","type": "atm","address": {"street1": "4660 La Jolla Village Drive","street2": "Suite 300","city": "San Diego","state": "CA","zip_code": "92122"}},{"id": "ae648760-7e77-11e5-8059-a1020f32cce5","type": "atm","address": {"street1": "New Orchard Road","city": "Armonk","state": "NY","zip_code": "10504"}},{"id": "c23397f0-7e76-11e5-8059-a1020f32cce5","type": "branch","phone": "512-286-5000","address": {"street1": "11400 Burnet Rd.","city": "Austin","state": "TX","zip_code": "78758-3415"}},{"id": "ca841550-7e77-11e5-8059-a1020f32cce5","type": "atm","address": {"street1": "334 Route 9W","city": "Palisades","state": "NY","zip_code": "10964"}},{"id": "dc132eb0-7e7b-11e5-9038-55f9f9c08c06","type": "branch","phone": "978-899-3444","address": {"street1": "550 King St.","city": "Littleton","state": "MA","zip_code": "01460-1250"}},{"id": "e1161670-7e76-11e5-8059-a1020f32cce5","type": "branch","phone": "561-893-7700","address": {"street1": "5901 Broken Sound Pkwy. NW","city": "Boca Raton","state": "FL","zip_code": "33487-2773"}},{"id": "f9ca9ab0-7e7b-11e5-9038-55f9f9c08c06","type": "atm","address": {"street1": "1 Rogers Street","city": "Cambridge","state": "MA","zip_code": "02142"}}]
    ```
    
Refer to Okta OIDC docs for endpoints and response payloads

https://developer.okta.com/docs/reference/api/oidc/
