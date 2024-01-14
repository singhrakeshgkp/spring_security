package com.security.reactiveapp;

import java.time.Duration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactiveAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveAppApplication.class, args);
	}
}

@RestController
class ReactiveTestController{
	@GetMapping("/test")
	public Flux<String> getmessages(){
		return Flux.just("hi ","welcome ","to ","this ","session").delayElements(Duration.ofSeconds(1));
	}

	@GetMapping("/greet")
	public Flux<String> greet(){
		return Flux.just("GM ","GE ","GA ","GN ","good bye").delayElements(Duration.ofSeconds(1));
	}
}
