package com.security.myauthserver.service;

import com.security.myauthserver.entity.User;
import com.security.myauthserver.model.Oauth2SecurityUser;
import com.security.myauthserver.repo.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUserName(username);
    return user.map(Oauth2SecurityUser::new).orElseThrow(()->new UsernameNotFoundException("user name not found"));

  }
}
