package com.security.resourceserver.config;

import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class AuthenticationTokenConverter implements Converter<Jwt, JwtAuthenticationToken> {

  @Override
  public JwtAuthenticationToken convert(Jwt source) {
    List<String> authorities = (List<String>) source.getClaims().get("authorities");
    JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(source,authorities.stream().map(
        SimpleGrantedAuthority::new).toList());
    return authenticationToken;
  }
}
