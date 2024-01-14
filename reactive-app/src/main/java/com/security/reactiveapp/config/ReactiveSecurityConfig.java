package com.security.reactiveapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class ReactiveSecurityConfig {

  @Bean
  SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .authorizeExchange(exchange->exchange.pathMatchers("/test")
            .hasAuthority("read"))
        .authorizeExchange(a->a
            .anyExchange()
            .authenticated())
        .httpBasic(Customizer.withDefaults())
        .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
  }

  @Bean
  ReactiveUserDetailsService userDetailsService(){
    var user1 = User.withUsername("rakesh")
        .password(passwordEncoder().encode("123"))
        .authorities("read")
        .build();

    var user2 = User.withUsername("singh")
        .password(passwordEncoder().encode("123"))
        .build();
    return new MapReactiveUserDetailsService(user1,user2);
  }

  @Bean
  PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
}
