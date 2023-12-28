package com.security.basic;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	@PreAuthorize("hasAuthority('delete')")
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
	@PreAuthorize("hasAuthority('read')")
	public String read(){
		var authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		authorities.forEach(authority -> System.out.println(authority.getAuthority()));
		return "performing read operation";
	}

	@GetMapping("/user/{name}")
	@PreAuthorize(
 "(#userName==authentication.name) or hasAnyAuthority('write','delete')") // if userName != name return 403 forbidden.
	// User name in pathVariable and userName what we pass in credential should match
	// ex. user Name rakesh, pwd 123, url = localhost:8181//user/rakesh this will work
	public String userName(@PathVariable("name") String userName ){
		return "performing userName operation "+userName;
	}

	@GetMapping("/user2/{name}")
	@PreAuthorize("@securityEvaluator.expression()")
	public String userName2(@PathVariable("name") String userName ){
		return "performing userName2 operation "+userName;
	}

	@GetMapping("/post-auth-ex/{value}")
	@PostAuthorize("returnObject != 'singh'")
	public String postAuthEx(@PathVariable String value){
		return value;
	}

	@GetMapping("pre-filter-ex")
	@PreFilter("filterObject.contains('singh')")
	public List<String> prefilterEx(@RequestBody List<String> names){
		return  names;
	}

	@GetMapping("post-filter-ex")
	@PostFilter("filterObject.contains('singh')")
	public List<String> postfilterEx(){
		List<String> names = new ArrayList<>();
		names.add("rakesh singh");
		names.add("mahendra");
		names.add("rajesh singh");
		return  names;
	}

}

@Component
class SecurityEvaluator{
  public boolean expression() {
    return true;
	}
}