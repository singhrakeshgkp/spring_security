package com.security.resourceserver.config;

import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class CustomJWTAuthenticationToken  extends JwtAuthenticationToken {

  private  String customField;
  public CustomJWTAuthenticationToken(Jwt jwt, List<SimpleGrantedAuthority> authorities) {
    super(jwt,authorities);
    this.customField = "jwt token custom value";
  }
}
