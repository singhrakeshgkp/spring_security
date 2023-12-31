package com.security.resourceserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ResourceServerSecurityConfig {

  @Value("${myauthserver.introspection.uri}")
  private String instrospectionUri;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.oauth2ResourceServer(
        r -> {
         r.opaqueToken(customizer->{
           customizer.introspectionUri(instrospectionUri);
           customizer.introspectionClientCredentials("client","testsecret");//Hardcoded
         });
        });
    httpSecurity.authorizeHttpRequests(
        authCustomizer -> authCustomizer.anyRequest().authenticated());
    return httpSecurity.build();
  }
}
