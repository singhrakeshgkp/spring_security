package com.security.basic;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SecurityBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityBasicApplication.class, args);
	}

}

@Controller
class TestController{


	@GetMapping("/transfer-money")
	public String transerMoney(){
		return "transfer.html";
	}
	@PostMapping(value = "/transfer-money")
	@ResponseBody
	public String transfer(@ModelAttribute MoneyTransferReq moneyTransferReq){
		System.out.println(moneyTransferReq.toString());
		return "transferred";
	}
}

@Data
class MoneyTransferReq{
	private int amount;
	private String currency;

}