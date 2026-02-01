package com.security.basic.config;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CuatomAuthenticationManager implements AuthenticationManager {

  private final CustomAuthenticationProvider authenticationProvider;
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    if(authenticationProvider.supports(authentication.getClass())){
      return  authenticationProvider.authenticate(authentication);
    }
    throw new BadCredentialsException("Invalid credential");
  }
}