package com.security.basic.config;

import static org.springframework.security.crypto.password.NoOpPasswordEncoder.*;

import com.security.basic.entity.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
public class SecurityConfig {
  /*@Bean
  UserDetailsService userDetailsService(){
    var uds = new InMemoryUserDetailsManager();
    var user1 = User.withUsername("user")
        .password("123")
        .build();
    uds.createUser(user1);
    return uds;
  }*/

  @Bean
  public PasswordEncoder passwordEncoder(){
    return getInstance();
  }

  @Bean
  public RoleHierarchy roleHierarchy(){
    var roleHierarchy = new RoleHierarchyImpl();
    /*Below syntax will define role as well. For more details refer spring doc for role hierarchy.
    * Admin ----> Admin will have Admin, staff and user role
    *   Staff -----> staff will have staff and user role
    *     User----> User will have only user role
    *     * */
    String hierarchy = "ROLE_ADMIN > ROLE_STAFF \n  ROLE_STAFF > ROLE_USER";
    roleHierarchy.setHierarchy(hierarchy);
    return  roleHierarchy;
  }


  @Bean
  static MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
    DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
    expressionHandler.setRoleHierarchy(roleHierarchy);
    return expressionHandler;
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity.authorizeHttpRequests(auth->auth.anyRequest().authenticated().)

    httpSecurity.authorizeHttpRequests(authorize->authorize
        .requestMatchers("/admin")
        .hasRole("ROLE_admin")
        .requestMatchers("/user")
        .hasRole("ROLE_user")
        .requestMatchers("/staff")
        .hasRole("ROLE_staff")
        .anyRequest()
        .authenticated()

    );
    return  httpSecurity.build();
  }
}
