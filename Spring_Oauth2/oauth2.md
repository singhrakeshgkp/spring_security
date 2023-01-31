# Oauth2 Integration

### About OAuth2
- Oauth2 high level [diagram](/spring_security/Spring_Oauth2/oauth2_highlevel_diagram.jpg)
- Oauth2 sequence [diagram](/spring_security/Spring_Oauth2/oauth2_sequence_diagram.jpg)
- Oauth server and resource server can be in the same application, ony drawback of this approach will be that you wont be able to scale individual service horizontally.
- Token can be two types
  - Opeque - it do not contains any data
  - non-Opeque- it contains data, JWP implementatio uses non-opeque based token
  - Refresh tokey grant type can be used to obtain new session in the same session
  - <b>Q. How does resource server verify if token is valid? </b>
  - Ans. This is because the token is signed and resource server will have key that can validate the signature. this happens only in case of non-opeque toke, if token is type of opeque resource server will call /introspection endpoint defined on authorization server.

### Configure AuthServer with Inmemory Credential
- Create new spring starter project with sping web and oauth2 dependency
  ```
  <dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-oauth2-authorization-server</artifactId>
			<version>1.0.0</version>
		</dependency>
  ```
- Create a new config class ```AuthServerConfig.java``` and configure the required beans.

