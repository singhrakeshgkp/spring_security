package com.security.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SecurityBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityBasicApplication.class, args);
	}

}

@RestController
class SecurityController{
	@GetMapping("/security-test")
	public String test(){
		var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		authorities.forEach(authority -> System.out.println(authority.getAuthority()));
		return "welcome to spring security";
	}
	@DeleteMapping("/delete")
	public String delete(){
		var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    authorities.forEach(authority -> System.out.println(authority.getAuthority()));
		return "performing delete operation";
	}
	@PostMapping("/write")
	public String write(){
		var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		authorities.forEach(authority -> System.out.println(authority.getAuthority()));
		return "performing write operation";
	}

	@GetMapping("/read")
	public String read(){
		var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		authorities.forEach(authority -> System.out.println(authority.getAuthority()));
		return "performing read operation";
	}
}

