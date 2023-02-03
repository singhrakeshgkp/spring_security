package com.security.authserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import com.security.authserver.entity.Client;
import com.security.authserver.repo.ClientRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements RegisteredClientRepository{

	@Autowired
	private ClientRepo clientRepo;
	
	@Override
	public void save(RegisteredClient registeredClient) {
		clientRepo.save(Client.from(registeredClient));
	}

	@Override
	public RegisteredClient findById(String id) {
		var client = clientRepo.findById(Integer.valueOf(id)).get();
		return Client.from(client);
	}

	@Override
	public RegisteredClient findByClientId(String clientId) {
		var client = clientRepo.findByClientId(clientId).get();
		RegisteredClient regClient = Client.from(client);
		return regClient;
	}

}
