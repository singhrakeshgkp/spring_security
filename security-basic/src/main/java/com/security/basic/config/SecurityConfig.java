package com.security.basic.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

  private  final CustomAuthenticationFilter customAuthenticationFilter;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .addFilterAt(customAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(auth->auth.anyRequest().authenticated())
        .build();
  }
}