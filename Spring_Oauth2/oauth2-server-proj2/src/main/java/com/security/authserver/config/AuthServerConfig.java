package com.security.authserver.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class AuthServerConfig {

	@Bean
	@Order(1)
	public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
	
		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
		httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
					.oidc(Customizer.withDefaults());
		
		httpSecurity.exceptionHandling(
				e->e.authenticationEntryPoint(
                   new LoginUrlAuthenticationEntryPoint("/login")
						)
				);
		return httpSecurity.build();
	}
	
	@Bean
	@Order(2)
	public SecurityFilterChain appSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.formLogin()
					.and()
					.authorizeHttpRequests()
					.anyRequest()
					.authenticated();
		return httpSecurity.build();
	}
	
	/*
	 * @Bean public UserDetailsService userDetailsService() { var user =
	 * User.withUsername("testuser") .password("12345") .authorities("read")
	 * .build(); var userDetailsservice = new InMemoryUserDetailsManager(user);
	 * return userDetailsservice; }
	 */

	@Bean
	public PasswordEncoder getPwdEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	/*
	 * @Bean public RegisteredClientRepository registeredClientRepository() {
	 * RegisteredClient rc = RegisteredClient.withId(UUID.randomUUID().toString())
	 * .clientId("client1") .clientSecret("secret21") .scope(OidcScopes.OPENID)
	 * .scope(OidcScopes.PROFILE) .redirectUri("https://springone.io/authorized")
	 * .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
	 * .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
	 * .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
	 * .tokenSettings(TokenSettings.builder()
	 * .accessTokenTimeToLive(Duration.ofSeconds(800))
	 * .accessTokenFormat(OAuth2TokenFormat.REFERENCE) .build())
	 * 
	 * 
	 * .build(); return new InMemoryRegisteredClientRepository(rc); }
	 */
	
	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}
	
	//JWK Key can be should be configured in some other source rather than memory such as db
	@Bean
	public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
	   kpg.initialize(2048);
	   KeyPair kp = kpg.generateKeyPair();
	   
	   RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
	   RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
	   RSAKey key = new RSAKey.Builder(publicKey)
			   				  .privateKey(privateKey)
			   				  .keyID(UUID.randomUUID().toString())
			   				  .build();
	   JWKSet set = new JWKSet(key);
	   return new ImmutableJWKSet<>(set);
	   
	   
	   
	}
	
	//Customizing jwt token
	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> customizer(){
		return context->{
			context.getClaims().claim("demo", "demo123");
		List<String> authorities =	context.getPrincipal().getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.toList());
		context.getClaims().claim("authorities", authorities);
		};
	}
	
}
