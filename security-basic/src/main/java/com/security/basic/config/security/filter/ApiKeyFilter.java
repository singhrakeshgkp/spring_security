package com.security.basic.config.security.filter;

import com.security.basic.config.security.CustomAuthenticationManager;
import com.security.basic.config.security.authentication.ApiKeyAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@AllArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter{

  private String key;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    var authenticationManager = new CustomAuthenticationManager(key);
    var keyFromHeader = request.getHeader("x-api-key");

    if("null".equals(keyFromHeader) || keyFromHeader == null){
      filterChain.doFilter(request,response);
    }

    var authentication = new ApiKeyAuthentication(keyFromHeader);
    var authenticationResult = authenticationManager.authenticate(authentication);
    try{
      if(authenticationResult.isAuthenticated()){
        SecurityContextHolder.getContext().setAuthentication(authenticationResult);
        filterChain.doFilter(request,response);
      }else{
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      }
    }catch(AuthenticationException ex){
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

  }
}
