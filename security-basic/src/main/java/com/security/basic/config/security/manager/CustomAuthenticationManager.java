package com.security.basic.config.security.manager;

import com.security.basic.config.security.provider.ApiKeyAuthenticationProvider;
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
   var authProvider = new ApiKeyAuthenticationProvider(key);
    if(authProvider.supports(authentication.getClass())){
      return  authProvider.authenticate(authentication);
    }
    return authentication;
  }
}