package com.security.basic.config;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
@Component
@AllArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter{

  private CuatomAuthenticationManager authenticationManager;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    /*1. create and authentication object which is not yet authenticated
     * 2. delegate the authentication object to the manager
     * 3. get back authentication from manager
     * 4 if the object is authenticated send the request to next filter in the chain*/
    String key = String.valueOf(request.getHeader("key"));
    CustomAuthentication customAuthentication = new CustomAuthentication(false,key);
    var authentication = authenticationManager.authenticate(customAuthentication);
    if(authentication.isAuthenticated()){
      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request,response);// only when authentication worked
    }

  }
}