package com.security.authserver.entity;

import java.time.Duration;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name="clients")
@AllArgsConstructor
@NoArgsConstructor
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "client_id")
	private String clientId;
	
	@Column(name = "secret_key")
	private String secretKey;
	
	@Column(name = "scope")
	private String scope;
	
	@Column(name = "auth_method")
	private String authMethod;
	
	@Column(name = "grant_type")
	private String grantType;
	
	@Column(name = "redirect_url")
	private String redirectUrl;

    
	public static Client from(RegisteredClient registeredClient) {
		
		//here client and grantypes, authenticationmethods can be associated with one to many relations
		Client client = Client.builder()
							  .clientId(registeredClient.getClientId())
							  .secretKey(registeredClient.getClientSecret())
							  .redirectUrl(registeredClient.getRedirectUris().stream().findAny().get())
							  .scope(registeredClient.getScopes().stream().findAny().get())
							  .authMethod(registeredClient.getClientAuthenticationMethods().stream().findAny().get().getValue())
							  .grantType(registeredClient.getAuthorizationGrantTypes().stream().findAny().get().getValue())
							  .build();
							  
		return client;
	}
	
public static RegisteredClient from(Client client) {
	
	RegisteredClient rgClient = RegisteredClient.withId(String.valueOf(client.getId()))
	   .clientId(client.getClientId())
	   .clientSecret(client.getSecretKey())
	   .scope(client.getScope())
	   .redirectUri(client.getRedirectUrl())
	   .clientAuthenticationMethod(new ClientAuthenticationMethod(client.getAuthMethod()))
	   .authorizationGrantType(new AuthorizationGrantType(client.getGrantType()))
	   .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(24)).build())
	   .build();
	return rgClient;
}
	
}
