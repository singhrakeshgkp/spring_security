package com.security.resourceserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ResourceServerConfig {

	@Value("${jwk.uri}")
	private String jwkUri;
	
	@Bean 
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.oauth2ResourceServer(
		r->r.jwt().jwkSetUri(jwkUri)	
		.jwtAuthenticationConverter(new JwtCustomTokenConverter())
		);
		http.authorizeHttpRequests().anyRequest().authenticated();
		return http.build();
	}
	
}
