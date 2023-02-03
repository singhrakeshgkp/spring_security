package com.security.authserver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.authserver.entity.User;
import com.security.authserver.model.UserDetailsAuthServerModel;
import com.security.authserver.repo.UserRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user = userRepo.findByUsername(username);
		return user.map(UserDetailsAuthServerModel::new).orElseThrow(()->new UsernameNotFoundException("user not found"));
	}

}
