package com.securitytest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.securitytest.entity.User;
import com.securitytest.model.UserDetailsImpl;
import com.securitytest.repo.UserRepo;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> dbUser = userRepo.findByEmail(username);
		
		return dbUser.map(UserDetailsImpl::new)
		.orElseThrow(()-> new UsernameNotFoundException("user name not found "+username));
		
	}

}
