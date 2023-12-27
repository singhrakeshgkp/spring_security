package com.security.basic.config.security.provider;

import com.security.basic.config.security.authentication.ApiKeyAuthentication;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@AllArgsConstructor
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

  @Value("${secret.key}")
  private String key;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    ApiKeyAuthentication apiKeyAuthentication = (ApiKeyAuthentication) authentication;
    if(apiKeyAuthentication.getKey().equals(key)){
      authentication.setAuthenticated(true);
      return authentication;
    }
     throw new BadCredentialsException("");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return ApiKeyAuthentication.class.equals(authentication);
  }
}
