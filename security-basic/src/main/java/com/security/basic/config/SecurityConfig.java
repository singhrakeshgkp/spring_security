package com.security.basic.config;

import com.security.basic.config.security.filter.ApiKeyFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Value("${secret.key}")
  private String key;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .addFilterBefore(new ApiKeyFilter(key), BasicAuthenticationFilter.class)
        .httpBasic(Customizer.withDefaults())
        .authorizeHttpRequests(authorize->authorize.anyRequest()
            .authenticated())
        .build();
  }
}
