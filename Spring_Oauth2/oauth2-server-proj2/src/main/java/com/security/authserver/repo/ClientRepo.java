package com.security.authserver.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.security.authserver.entity.Client;

public interface ClientRepo extends JpaRepository<Client, Integer>{

	@Query("Select c from Client c where c.clientId=:clientId")
	Optional<Client> findByClientId(String clientId);
}
