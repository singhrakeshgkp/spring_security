package com.security.resourceserver.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ResourceServerSecurityConfig {

  @Value("${myauth.server1.uri}")
  private String myAuthServer1;

  @Value("${myauth.server2.uri}")
  private String myAuthServer2;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.oauth2ResourceServer(
        resourceConfigurer-> resourceConfigurer.authenticationManagerResolver(authenticationManagerResolver())
    );
    httpSecurity.authorizeHttpRequests(
        authCustomizer -> authCustomizer.anyRequest().authenticated());
    return httpSecurity.build();
  }

  @Bean
  public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver(){
    JwtIssuerAuthenticationManagerResolver resolver =  JwtIssuerAuthenticationManagerResolver.fromTrustedIssuers(myAuthServer1,myAuthServer2);
    return resolver;
  }
}
