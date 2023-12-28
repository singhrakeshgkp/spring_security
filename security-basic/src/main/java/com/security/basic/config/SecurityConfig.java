package com.security.basic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    return httpSecurity
        .httpBasic(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)// not recommended, if not disable from postman post req will not work
        .authorizeHttpRequests(
            authorize ->
                authorize
                   .requestMatchers("write/**")
                    .hasAnyRole("admin")
                    .requestMatchers(HttpMethod.GET,"write/**")
                    .hasAnyAuthority("ROLE_admin","write")
                    .anyRequest()
                    .authenticated())
        .build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    var userDetailsService = new InMemoryUserDetailsManager();
    var user1 =
        User.builder()
            .username("rakesh")
            .password(passwordEncoder().encode("123"))
            .authorities("write", "read","delete")
            .roles("admin")
            .build();
    var user2 =
        User.builder()
            .username("suresh")
            .password(passwordEncoder().encode("1234"))
            .authorities("read")
            .roles("user")
            .build();
    userDetailsService.createUser(user1);
    userDetailsService.createUser(user2);
    return userDetailsService;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
