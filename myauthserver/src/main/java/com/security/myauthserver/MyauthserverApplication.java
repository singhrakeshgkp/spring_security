package com.security.myauthserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyauthserverApplication {
/*
*
*following url will generate authorization code
* localhost:8181/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=https://docs.spring.io/authorized&code_challenge=5WAPINVBjRCcYDdXWepIztBiZGTnYuXYQGUj-GgdA1I&code_challenge_method=S256
Code Challenge hash = 5WAPINVBjRCcYDdXWepIztBiZGTnYuXYQGUj-GgdA1I
Code verifier = 15ca9451bf43be316db1a1ad79fb9536c18a607daf3bef68fdfdce23
* */
	public static void main(String[] args) {
		SpringApplication.run(MyauthserverApplication.class, args);
	}

}
