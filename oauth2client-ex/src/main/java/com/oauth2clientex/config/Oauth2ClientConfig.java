package com.oauth2clientex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Oauth2ClientConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.oauth2Client(configurer->configurer.clientRegistrationRepository(clientRegistrationRepository()));
   httpSecurity.authorizeHttpRequests(registry->registry.anyRequest().permitAll());
    return httpSecurity.build();
  }

  @Bean
  public ClientRegistrationRepository clientRegistrationRepository(){
    ClientRegistration client1 = ClientRegistration.withRegistrationId("1")
        .clientId("client")
        .clientSecret("testsecret")
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)//for client credentials auth uri not needed
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .tokenUri("http://localhost:8181/oauth2/token")
        .scope(OidcScopes.OPENID)
        .build();
    InMemoryClientRegistrationRepository repository = new InMemoryClientRegistrationRepository(client1);

    return repository;
  }

  @Bean
  public OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager(ClientRegistrationRepository registrationRepository,
      OAuth2AuthorizedClientRepository repository){
    OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder()
        .authorizationCode()
        .refreshToken()
        .clientCredentials()
        .build();
    DefaultOAuth2AuthorizedClientManager manager = new DefaultOAuth2AuthorizedClientManager(registrationRepository,repository);
    manager.setAuthorizedClientProvider(provider);
    return  manager;
  }
}
