# OAuth2
## Table of contents
- [Oauth2](#oauth2)
   - [Oauth2 basic](#oauth2-basic)
      - [Grant Types](#grant-types)

## Oauth2 basic
- Basic [Diagram](/oauth2-basic.png)
- If u see the above diagram two questions arises which are below.
   - What are the possible grant types?
   - how does the resource server validate and get the data with the access token?
### Grant Types
- Authorization Code [diagram](/oauth2-authorizationcode.png)
   - user try to do something
   - request will go to client and then client will redirect the user to auth server login page with redirect url.
   - user will provide the login details and send the request, request will go to auth server
   - Auth server will redirect the user on the required page. here its client page.
   - Client will send authorization code + client cred to auth server to get access token
   - Auth server will send back access token to client.
 
- Client Credentials [diagram](/oauth2-client-credentials.png)
   - In this type of grant type there is no user intraction
   - client send client id and secret to auth server
   - auth server returns the access token to client

