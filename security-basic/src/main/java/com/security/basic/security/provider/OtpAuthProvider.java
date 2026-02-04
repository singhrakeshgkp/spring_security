package com.security.basic.security.provider;

import com.security.basic.repo.OtpRepo;
import com.security.basic.security.authentication.OtpAuthentication;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class OtpAuthProvider implements AuthenticationProvider {

  @Autowired
  private OtpRepo otpRepo;
  @Override
  public @Nullable Authentication authenticate(Authentication authentication)
      throws AuthenticationException {
   String userName = authentication.getName();
   String password = (String) authentication.getCredentials();
    var otp = otpRepo.getOtpByUserName(userName);
    if (otp.isPresent()){
      return new OtpAuthentication(userName,otp, List.of(() -> "read"));
    }
    throw  new BadCredentialsException("Credential is not correct");
  }

  @Override
  public boolean supports(Class<?> authenticationClass) {
    return OtpAuthentication.class.equals(authenticationClass);
  }
}
