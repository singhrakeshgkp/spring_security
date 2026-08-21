
# Implementation
- OAuthServer
  - [With PKCE](#withpkce)
 

# With PKCE

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

3. run server and construct url similar to below using client id, pkce code challenge (can be generated from online tool), redirect url.....etc
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

