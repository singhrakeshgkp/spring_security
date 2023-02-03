package com.security.authserver.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.security.authserver.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	//define a method from UserDetailsService(core security class) named loadUserByUsername
	@Query("Select u from User u where u.userName=:userName")
	Optional<User> findByUsername(String userName);
}
