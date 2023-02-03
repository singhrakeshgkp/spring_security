package com.security.resourceserver.controller;



import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	
	/*
	 * Note Added Authentication  in method argument just to check Authentication value
	 *  value in debug time, 
	 */
	@GetMapping
	public String demoCtrl(Authentication auth) {
	
		return "Hi welcome to spring security resource server test";
	}
}
