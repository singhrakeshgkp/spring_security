package com.authserver;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

@Configuration
public class AuthServerConfig {

  @Bean
  public RegisteredClientRepository registeredClientRepository(){
    RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId("rks123")
        .clientSecret("secret")
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .scope(OidcScopes.OPENID)
        .redirectUri("http://spring.io/auth")
        .build();
    return new InMemoryRegisteredClientRepository(client);
  }

 public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
   KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
   keyPairGenerator.initialize(2048);
   KeyPair keyPair = keyPairGenerator.generateKeyPair();
   RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
   RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
   RSAKey rsaKey = new RSAKey.Builder(publicKey)
       .privateKey(privateKey)
       .keyID(UUID.randomUUID().toString())
       .build();
   JWKSet jwkSet = new JWKSet(rsaKey);
    return new ImmutableJWKSet<>(jwkSet);
 }
}
