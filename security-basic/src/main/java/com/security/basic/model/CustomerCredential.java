package com.security.basic.model;

import com.security.basic.entity.Customer;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@NoArgsConstructor
public class CustomerCredential implements UserDetails {

  private  Customer customer;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(()->"read");
  }

  @Override
  public String getPassword() {

    return customer.getPassword();
  }

  @Override
  public String getUsername() {
    return customer.getUserName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;//default is false
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;//default is false
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true; //default is false
  }

  @Override
  public boolean isEnabled() {
    return true;//default is false
  }
}