package com.securitytest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banklist")
public class BankController {
	
	@GetMapping
	public String getBankList() {
		return "here is the bank list";
	}

}
