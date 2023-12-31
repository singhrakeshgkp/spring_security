package com.security.myauthserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Duration;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

@Entity
@Table(name = "oauth2_clients")
@Getter
@Setter
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name="client_id")
  private String clientId;

  @Column(name="client_secret")
  private String secret;

  @Column(name = "scope")
  private String scope;

  @Column(name = "grant_type")
  private String grantType;

  @Column(name = "auth_method")
  private String authMethod;

  @Column(name = "redirect_uri")
  private String redirectUri;

  public static Client from(RegisteredClient registeredClient){
    Client client = new Client();
    client.setClientId(registeredClient.getClientId());
    client.setSecret(registeredClient.getClientSecret());
    client.setScope(registeredClient.getScopes().stream().findAny().get());
    client.setRedirectUri(registeredClient.getRedirectUris().stream().findAny().get());
    client.setGrantType(registeredClient.getAuthorizationGrantTypes().stream().findAny().get().getValue());
    client.setAuthMethod(registeredClient.getClientAuthenticationMethods().stream().findAny().get().getValue());
    return client;
  }

  public static RegisteredClient from(Client client) {
   RegisteredClient registeredClient =  RegisteredClient.withId(String.valueOf(client.getId()))
       .clientSecret(client.getSecret())
       .clientId(client.getClientId())
       .scope(client.getScope())
       .redirectUri(client.getRedirectUri())
       .clientAuthenticationMethod(new ClientAuthenticationMethod(client.getAuthMethod()))
       .authorizationGrantType(new AuthorizationGrantType(client.getGrantType()))
       .tokenSettings(TokenSettings.builder().accessTokenTimeToLive(Duration.ofHours(20)).build())
       .build();
   return registeredClient;
  }
}
