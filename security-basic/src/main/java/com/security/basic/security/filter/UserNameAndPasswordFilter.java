package com.security.basic.security.filter;

import com.security.basic.entity.Otp;
import com.security.basic.repo.OtpRepo;
import com.security.basic.security.authentication.OtpAuthentication;
import com.security.basic.security.authentication.UserNameAndPasswordAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@AllArgsConstructor
public class UserNameAndPasswordFilter extends OncePerRequestFilter {

  private AuthenticationManager authenticationManager;
  private OtpRepo otpRepo;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    /*step 1- username & pass
    * step 2- username & otp*/

    var username = request.getHeader("username");
    var password = request.getHeader("password");
    var otp = request.getHeader("otp");
    if (otp == null){
      Authentication usernameAndPassAuthentication = new UserNameAndPasswordAuthentication(username,password);
      /*Generate otp and save in DB*/
      String code = String.valueOf(new Random().nextInt(9999)+1000);
      Otp o = new Otp();
      o.setUserName(username);
      o.setOtp(code);
      otpRepo.save(o);

      SecurityContextHolder.getContext().setAuthentication(usernameAndPassAuthentication);
    }else{
      Authentication otpAuthentication = new OtpAuthentication(username,otp);
      otpAuthentication= authenticationManager.authenticate(otpAuthentication);
     // issue a token
      response.setHeader("Authorization", UUID.randomUUID().toString());
    }
  }


  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return !request.getServletPath().equals("/login");
  }

}
