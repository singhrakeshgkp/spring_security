package com.security.basic.model;

import com.security.basic.entity.Authority;
import com.security.basic.entity.Customer;
import com.security.basic.entity.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@NoArgsConstructor
public class CustomerCredential implements UserDetails {

  private  Customer customer;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return getAuthoritiesByRoles(customer.getRoles());
  }

  private Collection<? extends GrantedAuthority> getAuthoritiesByRoles(Set<Role> roles) {
    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    List<String> rolesAndAuthoritiesNames = new ArrayList<>();
    Collection<Authority> authorities = new ArrayList<>();
     for(Role role : roles){
       grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
       authorities.addAll(role.getAuthorities());
     }
     for(Authority authority : authorities){
       grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
     }
     return grantedAuthorities;
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
