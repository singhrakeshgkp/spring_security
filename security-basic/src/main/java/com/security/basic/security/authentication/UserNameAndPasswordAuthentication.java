package com.security.basic.security.authentication;

import java.util.Collection;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserNameAndPasswordAuthentication extends UsernamePasswordAuthenticationToken {

  public UserNameAndPasswordAuthentication(@Nullable Object principal,
      @Nullable Object credentials) {
    super(principal, credentials);
  }

  public UserNameAndPasswordAuthentication(Object principal, @Nullable Object credentials,
      Collection<? extends GrantedAuthority> authorities) {
    super(principal, credentials, authorities);
  }

  protected UserNameAndPasswordAuthentication(Builder<?> builder) {
    super(builder);
  }
}
