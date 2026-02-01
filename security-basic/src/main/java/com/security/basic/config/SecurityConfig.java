package com.security.basic.config;

import com.security.basic.config.filter.ApikeyFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {
  @Value("${secret.key}")
  private String key;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .addFilterBefore(new ApikeyFilter(key), BasicAuthenticationFilter.class)
        .httpBasic(Customizer.withDefaults())
        .authorizeHttpRequests(auth->auth.anyRequest().authenticated())
        .build();
  }
}