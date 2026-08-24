
# Implementation
- OAuthServer
  - [Oauth2 Apis](#oauth2-apis)
  - [JWT](#jwt)
  - [Generate JWT Token using PKCE Proj abcAuthServer](#generate-jwt-token-using-pkce-proj-abcAuthServer)
    - [Configure Custom Public and Private Key](#configure-custom-public-and-private-key)
  - [Configure abcAuthServer2 Proj](#Configure-abcAuthServer2-Proj)
    - [Customize jwt token](#customize-jwt-token)
  - [Configure Resource Server](#configure-resource-server)
  - [QA](#qa)
  
# Oauth2 Apis
- ```/.well-known/openid-configuration``` provides ready to use endpoint configured by spring teams, few important endpoint is listed below
  1. ```/oauth2/authorize``` ---> use to get authorization code
  2. ```/oauth2/token```----> return token ex access_token, id_token
  3. ```oauth2/jwks```----> return kid and public key, resource server uses this api to get public key and the resource server uses this public key to verify the signature of token              passed by client(applicable for non-opaque token)
  4. if its opaque token resource server uses ```/oauth2/introspect```
# JWT
- JWT JSON web token, it contains three part
  - **Header** --> Contains metadata example type, alg (algorithm used)
  - **Payload** --> The data claims
  - **Signature** --> The security seal, its most crucial part because it guarantees the token integrity and authenticity, it ensures that the client or hacker hasn't tampered with user role or expiration dates in payload
- JWT must be signed.
- When we create authserver using spring boot, spring boot auto configuration generate private key and public key (if not provided) and sign JWT token. This configuration is recommended for development purpose(limited to local). Since public and private key is in-memory so on each server restart it will be lost and client who already generated token 

# Generate JWT Token using PKCE Proj abcAuthServer

#### Note---> Generated authorization code can be used only once no matter if request fail or pass. 
- In PKCE flow we will use code challenge and code verifier with client id , we will not use client secret.
- **Code Verifier**---> Random string, kind of secret key generated every time before login process
- **Code challenge** ----> Transformed version of code verifier, hashes it using the SHA-256 cryptographic algorithm. Because it is a one-way hash, someone who sees the challenge cannot reverse-engineer it to figure out your verifier.
1. Create new spring boot application with spring web and oauth2 server dependency
2. Configure RegisteredClientRepository bean as shown below, since its PKCE consider following things
  ```
     --------do not configure ---------
    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
     .clientSecret(passwordEncoder.encode("1122"))

    ------ Add following---------------
     .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
  ```

  ```
     @Bean
  public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder){
    RegisteredClient registeredClient = RegisteredClient
        .withId(UUID.randomUUID()
            .toString()
        )
        .clientId("abc")
        .scope(OidcScopes.OPENID)
        .scope(OidcScopes.PROFILE)
        .redirectUri("https://spring.io/authtest") // configured dummy url, we can configure any actual client uri
        .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
           .build();
    return new InMemoryRegisteredClientRepository(registeredClient);
  }
  ```

3. run server and construct url similar to below using client id, PKCE code challenge (PKCE code challenge can be generated from online tool), redirect url.....etc
4. ```http://localhost:8080/oauth2/authorize?response_type=code&client_id=abc&scope=openid&redirect_uri=https://spring.io/authtest&code_challenge=9NJRWMzdgsJXkFWwRU79HtmBqeZw0tj_lwfx-oEwrYY&code_challenge_method=S256```
5. Paste above url in browser, login with user credential---> redirect on given redirect_url with code (authorization code). copy authorization and use it with following curl command you will be able to generate jwt token
``` postman request POST 'localhost:8080/oauth2/token' \
  --header 'Cookie: JSESSIONID=1BFD96ED09C0F54C4FCF0A16E796A6B7' \
   --form 'client_id=abc' \
   --form 'redirect_uri=https://spring.io/authtest' \
   --form 'grant_type=authorization_code' \
   --form 'code=d1LN49BqjvFWmamUd0mMBVugdtYQjWG0A2a4rrvGK6L-woSN-NqzTFpRo5wMmHBVPA-3QestLhrarBP-2o_60z03mvEfNcgMmh1O47w9pZ14_FPgmJHJiCZj2Pqkuacp' \
   --form 'code_verifier=singh' 
```

## Configure Custom Public and Private Key
- Except local we always configure custom private key and public key, reason is discussed in ```QA section what if in prod we use in-memory key pair?```
- Before configuring public key and private key, we can test in-memory public key if its chanting after server restart
  - Access ```localhost:8080/oauth2/jwks``` endpoint restart server. if u observe response each time u restart server ```/jwks``` endpoint will return every time different ```kid```
- Steps to configure own public key and private key is given below
  1. Generate a Private and public key using below command, .p12 contains both public key and private key.
     ```
      keytool -genkeypair \
      -alias auth-server-key \
      -keyalg RSA \
      -keysize 2048 \
      -validity 365 \
      -keystore src/main/resources/auth-server.p12 \
      -storetype PKCS12 \
      -storepass mykeystorepass
     ```
  3. you can verify generated .p12 file using ```keytool -list -v -keystore src/main/keytool -list -v -keystore src/main/resources/auth-server.p12 -storepass mykeystorepass``` command
  4. Now configure ```jwkSource``` beans, i have configured it in ```JwtKeyConfig.java``` file
  5. Run it and restart server you will see public key and kid will be same


# Configure abcAuthServer2 Proj
## Everything is same what we have in abcAuthServer project only changes in this abcAuthServer2 is it will not use in memory user credential, its going to use DB
- Configured UserDtService, User entity, UserRepo, and created UserDtModel this we will be using to map our User data from DB to Spring security User model
- Run application and test if token is generated

## Customize jwt token
- by default spring do not include roles/authority in jwt token, to include that configure following bean

  ```
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> oAuth2TokenCustomizer(){
      return  context ->  {
        var authorities = context.getPrincipal().getAuthorities();
        context.getClaims()
          .claim("authorities", authorities.stream().map(authority-> authority.getAuthority()).toList());
    };
  }
  ```

# Configure Resource Server
- Create new spring boot application with spring web, spring security and oauth2-resource server dependency.
- 

# QA
### what if in prod we use in-memory key pair?
- Since public key and private key is stored in in-memory next time when auth server restart, it will generate different public key. Resource server will not be able to validate signature of existing token which might not be expired yet.
- **Scenario 1** ---> (1)client--Generates token access any resource--> (2) auth sever restarted-->(3) client try to do something again with token generated in step(1) in this case will request to resource server fail?
- **Explanation Scenario 1** ---> there are two cases
  - **Case 1 After restart no one make request with new token(its first call to resource server with existing token and kid TTL is not passed and cache is not flushed because of some other reason)** ---> Resource server will give success response, detailed explanation is given below
    - 1 Token generated by client and access any resource ---> in this step resource server will cache ```kid``` which will have public key in cache
    - 2 Auth server restarted ---> after restart auth server will generate new public and private key
    - 3  Client try to do something again with token generated in step(1) ---> Since its first request to resource server ```kid``` passed in token will match with ```kid``` in cache,          resource server will be able to validate signature successfully.
  - **Case 2 After restart before before client make any request with existing token to resource server, client generate new token for other user and make call to resource server**
    - When client makes call to resource server for some other use with new token, resource server will find mismatch in ```kid``` (kid in token and resource server cache), in this case resource server will make call to auth server--->get new public key---> store it in cache and validate signature. So all request with new token will success however all other request with old token will fail
    
### How spring boot store kid(key id) in cache, can kid be duplicate?
- Spring store kid(example kid=123-5jjj678) as key and Compiled Java object of public key as value.

  
  
