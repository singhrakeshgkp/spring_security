package com.security.basic.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Value("${secret.key}")
  private String key;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    CustomAuthentication customAuthentication = (CustomAuthentication) authentication;
    String keyFromHeader = customAuthentication.getKey();
    if(key.equals(keyFromHeader)){
      CustomAuthentication authenticationResult = new CustomAuthentication(true,null);
      return authenticationResult;
    }
    throw new BadCredentialsException("invalid credentials");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return CustomAuthentication.class.equals(authentication);
  }
}
