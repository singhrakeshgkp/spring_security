package com.security.resourceserver.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;
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
        resourceConfigurer-> resourceConfigurer
            .authenticationManagerResolver(authenticationManagerResolver(jwtDecoder(),opaqueTokenIntrospector()))
    );
    httpSecurity.authorizeHttpRequests(
        authCustomizer -> authCustomizer.anyRequest().authenticated());
    return httpSecurity.build();
  }

  @Bean
  public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver(JwtDecoder jwtDecoder,
      OpaqueTokenIntrospector opaqueTokenIntrospector){
    AuthenticationManager jwtAuthManager = new ProviderManager(
        new JwtAuthenticationProvider(jwtDecoder)
    );
    AuthenticationManager opaqueAuthManager = new ProviderManager(
        new OpaqueTokenAuthenticationProvider(opaqueTokenIntrospector)
    );

    return request->{
      if("JWT".equalsIgnoreCase(request.getHeader("auth_type"))){
        return jwtAuthManager;
      }else{
        return opaqueAuthManager;
      }
    };
  }

  @Bean
  public  JwtDecoder jwtDecoder(){
    return NimbusJwtDecoder.withJwkSetUri(myAuthServer1)
        .build();
  }
  @Bean
  public OpaqueTokenIntrospector opaqueTokenIntrospector(){
    return new SpringOpaqueTokenIntrospector(myAuthServer2,"client","testsecret");
  }
}
