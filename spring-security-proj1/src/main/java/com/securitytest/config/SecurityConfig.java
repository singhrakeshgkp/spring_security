package com.securitytest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {

	@Bean
	public UserDetailsService userDetailsService() {
		
		UserDetails adminUser = User.withUsername("user1")
						   .password(encoder().encode("root"))
						   .roles("ADMIN")
						   .build();
		UserDetails user = User.withUsername("user2")
				   .password(encoder().encode("root"))
				   .roles("USER")
				   .build();
		return new InMemoryUserDetailsManager(adminUser,user);
		
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
	  
		return httpSecurity.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers("/greet")
		.permitAll()
		.and()
		.authorizeHttpRequests()
		.requestMatchers("/customer/**").authenticated()
		.and()
		.formLogin()
		.and()
		.build();
	}
	
	@Bean
	public PasswordEncoder encoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	
}
