package com.security.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SecurityBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityBasicApplication.class, args);
	}

}

@RestController
class TestController{
	@GetMapping("/test")
	public String test(){
		return "Test -- CORS Browser based implemented security";
	}

	@GetMapping("/abc")
	public String abc(){
		return "ABC -- CORS Browser based implemented security";
	}
}
