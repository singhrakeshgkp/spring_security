# Oauth2

### About OAuth2
- Oauth2 high level [diagram](/spring_security/Spring_Oauth2/oauth2_highlevel_diagram.jpg)
- Oauth2 sequence [diagram](/spring_security/Spring_Oauth2/oauth2_sequence_diagram.jpg)
- Oauth server and resource server can be in the same application, ony drawback of this approach will be that you wont be able to scale individual service horizontally.
- ```http://localhost:8080/.well-known/openid-configuration``` Endpoints returns various endpoints and configuraton details.
- Token can be two types
  - Opeque - it do not contains any data
  - non-Opeque- it contains data, JWP implementatio uses non-opeque based token
  - Refresh tokey grant type can be used to obtain new session in the same session
  - <b>Q. How does resource server verify if token is valid? </b>
  - Ans. This is because the token is signed and resource server will have key that can validate the signature. this happens only in case of non-opeque toke, if token is type of opeque resource server will call /introspection endpoint defined on authorization server.

# Configure AuthServer(oauth2-server-proj1) with Inmemory Credential

- Create new spring starter project with sping web and oauth2 dependency
  ```
  <dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-oauth2-authorization-server</artifactId>
			<version>1.0.0</version>
		</dependency>
  ```
- Create a new config class ```AuthServerConfig.java``` and configure the required beans.
- <b>Testing Oauth2 application</b>
  - Generate code verifier and code challenge hash using any online PKCE generator such as ```https://example-app.com/pkce```
  - Append client id, code verifier, code challenge and authorization type in below url
   ```
   http://localhost:8080/oauth2/authorize?response_type=code&client_id=client1&scope=openid&redirect_uri=https://springone.io/authorized&code_challenge=y3jlyQ-YikWE5KLClCGlLoGJ3BulHDZitaJK7MQFByc&code_verifier=a06658bc1197e48494b6305e4ad074c8c08e1b22b316e2c1c5eb0562&code_challenge_method=S256

   ```
  - It will redirect you on login page, enter your credential and continue to login
  - Authorization code will be generated, you can copy it from url.
  - Now make a post request to get the access token
    - URL ```http://localhost:8080/oauth2/token```
    - Authentication - basic with client id and client secret and with following parameters
      ```
      "grant_type":"authorization_code"
      "code":"authorization code received from auth server"
      "redirect_uri":"https://springone.io/authorized"
      "code_verifier":"code verifier passed while getting the authorization code"
      ```
  - In case if you are not able to get jwt(access) token, i mean u get some error you have to generate authorization code again, as it can be used only once.
   
#### Customizing  token
- we can customize jwt token, we can add the fields as per our need that field will be part of jwt token.
  - To customize jwt token configure following bean in your config class.
    ```
  	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> customizer(){
		return context->{
			context.getClaims().claim("demo", "demo123");
		};
	}
    ```
  - start your application, generate the jwt token and verify if field present in token.
- Cutomizing token type (from non-opaque to opaque) and token time to live value
  - Use the below code snipped to customize toen type and token TTL value.
    ```
    	.tokenSettings(TokenSettings.builder()
	   .accessTokenTimeToLive(Duration.ofSeconds(800))
	   .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
	   .build())
    ```
# Configure AuthServer(oauth2-server-proj2) with database credential
- create a database and two tables ```user``` and ```client`` in it
- add mysql connector and spring data jpa dependency
- Crete entity classes
- Create repository class ```UserRepo.java``` and ```ClientRepo.java```
- Create ```UserDetailsServiceImpl.java``` class, this class should implement ```UserDetailsService(core spring security)``` interface.
- create new ```UserDetailsAuthServerModel.java``` class implementing UserDettails (from core security) interface.
- delete following bean from config class
  ```
   @Bean
	public UserDetailsService userDetailsService() {
		var user = User.withUsername("testuser")
					   .password("12345")
					   .authorities("read")
					   .build();
		var userDetailsservice = new InMemoryUserDetailsManager(user);
		return userDetailsservice;
	}
  ```
- Same thing need to do with Client, Creating ```ClientServiceImpl``` ........ etc
- Delete client bean from config class
- provide the requred database properties in application.properties file and start the application
- Test the application same way as we tested with in memory credential.

# Configure resource server
- Create new spring boot project with ```sping web, security, and resource server``` dependency
- Create a config class, configure SecurityFilterChain bean in it.
- Configure the jwk uri in resource server configuration. JWK uri can be found on ```http://localhost:8080/.well-known/openid-configuration``` end point of auth server.
- Now test the application
### Adding Authories in Authentication object
- we hadded the "authorities" in token in auth server, but if we debug and check Authorization obj in resources relevant authorities will be part of token but not authorities type obj.
- To incude authorities make the following changes.
  - Create a new class ```JwtCustomTokenConverter.java``` write the required code.
  - Add the following code in filter security chain bean  in config class of resource server.
   ```
   r->r.jwt().jwkSetUri(jwkUri)	
		.jwtAuthenticationConverter(new JwtCustomTokenConverter())
		);
   ```
- Now test the application in debug mode, authorities should be part of Authorization.authorities obj.

