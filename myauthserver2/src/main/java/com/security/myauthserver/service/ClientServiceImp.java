package com.security.myauthserver.service;

import com.security.myauthserver.entity.Client;
import com.security.myauthserver.repo.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Var;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImp implements RegisteredClientRepository {

private final ClientRepository clientRepository;
  @Override
  public void save(RegisteredClient registeredClient) {
    clientRepository.save(Client.from(registeredClient));
  }

  @Override
  public RegisteredClient findById(String id) {
    var client = clientRepository.findById(Integer.valueOf(id)).orElseThrow();
    return Client.from(client);
  }

  @Override
  public RegisteredClient findByClientId(String clientId) {
    var client = clientRepository.findByClientId(clientId).orElseThrow();
    return Client.from(client);
  }
}
