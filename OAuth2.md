# OAuth2
## Table of contents
- [Oauth2](#oauth2)
   - [Oauth2 basic](#oauth2-basic)
      - [Grant Types](#grant-types)
      - [How does resource server validate token](#how-does-resource-server-validte-token)

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
 
- Client Credentials [diagram](/client-credentials.png)
   - In this type of grant type there is no user intraction
   - client send client id and secret to auth server
   - auth server returns the access token to client
- Refresh token [diagram](/oauth2-refresh-token.png)
   - user send request to client
   - client request for token using refresh token
   - auth server send access token and refresh token
### How does resource server validate token
- Token could be of two types
  - Opaque---> this kind of token do not contains any data.
  - Non Opaque----> This kind of token contains data. ex JWT is the example of non opaque token
 
- How Resource server validate the token is depends on the type of token being used. If opaque token is being used resource serve make a call to ```/introspection``` end point of auth server to get the data. If its non opeque token in this case resource server do not make any call to oauth sever only validate the signature.
