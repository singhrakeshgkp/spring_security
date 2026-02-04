package com.security.basic.config;

import com.security.basic.repo.OtpRepo;
import com.security.basic.security.filter.UserNameAndPasswordFilter;
import com.security.basic.security.provider.OtpAuthProvider;
import com.security.basic.security.provider.UsernamePasswordAuthProvider;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig   {

  @Autowired
  private OtpRepo otpRepo;
  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(List<AuthenticationProvider> authenticationProviders){
    return new ProviderManager(authenticationProviders);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authManager) throws Exception {
    return http
        .addFilterBefore(new UserNameAndPasswordFilter(authManager,otpRepo),
            BasicAuthenticationFilter.class)
        .build();

  }
}