package com.securitytest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securitytest.entity.User;
import com.securitytest.service.CustomUserService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	public CustomUserService customUserService;
	
	@GetMapping
	public String getCustomers() {
		return "All Customer details are";
	}
	@GetMapping("/{id}")
	public String getCustomerById(@PathVariable Integer id) {
		return "customer details by id is :- "+id;
	}
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody User user){
		
	User savedUser =  customUserService.saveUser(user);
	return new ResponseEntity<User>(savedUser,HttpStatus.CREATED);
	}
}
