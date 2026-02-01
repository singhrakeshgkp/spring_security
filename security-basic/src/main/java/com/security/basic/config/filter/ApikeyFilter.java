package com.security.basic.config.filter;
import com.security.basic.config.authentication.CustomAuthentication;
import com.security.basic.config.manager.CustomAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@AllArgsConstructor
public class ApikeyFilter extends OncePerRequestFilter{

  private String key;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    /*1. create and authentication object which is not yet authenticated
     * 2. delegate the authentication object to the manager
     * 3. get back authentication from manager
     * 4 if the object is authenticated send the request to next filter in the chain*/
    String xApiKeyHeader = request.getHeader("x-api-key");

    if (xApiKeyHeader == null || "null".equals(xApiKeyHeader)){
      filterChain.doFilter(request,response);
    }
    var customAuthentication = new CustomAuthentication(false,xApiKeyHeader);
    var authenticationManager = new CustomAuthenticationManager(key);
    try{
      var authentication = authenticationManager.authenticate(customAuthentication);
      if(authentication.isAuthenticated()){
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
      }else{
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      }
    }catch(AuthenticationException ex){
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }
}