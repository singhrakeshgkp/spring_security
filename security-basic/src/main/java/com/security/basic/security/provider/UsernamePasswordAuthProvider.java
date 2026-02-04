package com.security.basic.security.provider;

import com.security.basic.security.authentication.UserNameAndPasswordAuthentication;
import com.security.basic.service.UserDtService;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthProvider implements AuthenticationProvider {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserDtService userDtService;
  @Override
  public @Nullable Authentication authenticate(Authentication authentication)
      throws AuthenticationException {
    String userName = authentication.getName();
    String password = (String) authentication.getCredentials();
    UserDetails user = userDtService.loadUserByUsername(userName);

    if (passwordEncoder.matches(password,user.getPassword())){
      return new UserNameAndPasswordAuthentication(userName,password, user.getAuthorities());
    }

    throw new  BadCredentialsException("Bad Credential");
  }

  @Override
  public boolean supports(Class<?> authenticationClass) {
    return UserNameAndPasswordAuthentication.class.equals(authenticationClass);
  }
}
