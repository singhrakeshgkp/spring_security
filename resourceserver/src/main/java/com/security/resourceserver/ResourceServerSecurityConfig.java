package com.security.resourceserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ResourceServerSecurityConfig {

  @Value("${myauthserver.jwk.uri}")
  private String jwkUri;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.oauth2ResourceServer(
        r->r.jwt(jwtConfigurer -> jwtConfigurer.jwkSetUri(jwkUri))
    );
    httpSecurity.authorizeHttpRequests(
        authCustomizer -> authCustomizer.anyRequest().authenticated());
    return httpSecurity.build();
  }
}
