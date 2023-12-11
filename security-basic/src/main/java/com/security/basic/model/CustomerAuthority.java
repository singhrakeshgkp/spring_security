package com.security.basic.model;

import com.security.basic.entity.Authority;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class CustomerAuthority implements GrantedAuthority {

  private final Authority authority;
  @Override
  public String getAuthority() {
    return authority.getName();
  }
}
