package com.security.basic.config;

import static org.springframework.security.crypto.password.NoOpPasswordEncoder.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {
  @Bean
  UserDetailsService userDetailsService(){
    var uds = new InMemoryUserDetailsManager();
    var user1 = User.withUsername("user")
        .password("123")
        .build();
    uds.createUser(user1);
    return uds;
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return getInstance();
  }
}
