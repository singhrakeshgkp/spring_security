
# Implementation
- OAuthServer
  - [With PKCE](#withpkce)
 

# With PKCE
- Create new spring boot application with spring web and oauth2 server dependency
- Configure RegisteredClientRepository bean as shown below, since its PKCE consider following things
  ```
     --------do not configure ---------
    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
     .clientSecret(passwordEncoder.encode("1122"))

    ------ Add following---------------
     .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
  ```

  - 
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
- run server, 
