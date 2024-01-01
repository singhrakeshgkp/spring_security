package com.oauth2clientex;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Oauth2clientExApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2clientExApplication.class, args);
	}

}

@RestController
@AllArgsConstructor
class TestController{
	private  final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager; //proxy

	@GetMapping("/token")
	public String getToken(){
		OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest.withClientRegistrationId("1")
				.principal("client")
				.build();
		OAuth2AuthorizedClient client =  oAuth2AuthorizedClientManager.authorize(request);
		 return client.getAccessToken().getTokenValue();
	}
}
