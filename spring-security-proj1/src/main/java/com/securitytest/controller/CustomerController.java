package com.securitytest.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getCustomers() {
		return "All Customer details are";
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String getCustomerById(@PathVariable Integer id) {
		return "customer details by id is :- "+id;
	}
}
