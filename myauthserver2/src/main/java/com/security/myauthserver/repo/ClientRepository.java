package com.security.myauthserver.repo;

import com.security.myauthserver.entity.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Integer> {


  Optional<Client> findByClientId(String clientId);
}
