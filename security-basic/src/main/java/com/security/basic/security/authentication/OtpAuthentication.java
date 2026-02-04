package com.security.basic.security.authentication;

import java.util.Collection;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class OtpAuthentication extends UsernamePasswordAuthenticationToken {

  public OtpAuthentication(@Nullable Object principal,
      @Nullable Object credentials) {
    super(principal, credentials);
  }

  public OtpAuthentication(Object principal, @Nullable Object credentials,
      Collection<? extends GrantedAuthority> authorities) {
    super(principal, credentials, authorities);
  }

  protected OtpAuthentication(Builder<?> builder) {
    super(builder);
  }
}
