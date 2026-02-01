package com.security.basic.config.manager;

import com.security.basic.config.provider.CustomAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

  private final String key;
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    var authenticationProvider = new CustomAuthenticationProvider(key);
    if(authenticationProvider.supports(authentication.getClass())){
      return  authenticationProvider.authenticate(authentication);
    }
    throw new BadCredentialsException("Invalid credential");
  }
}