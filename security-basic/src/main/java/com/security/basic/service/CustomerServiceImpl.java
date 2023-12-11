package com.security.basic.service;

import com.security.basic.model.CustomerCredential;
import com.security.basic.repository.CustomerRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements UserDetailsService,ICustomerService {

  private final CustomerRepo customerRepo;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
   var customer = customerRepo.findByUserName(username);
  return  customer.map(CustomerCredential::new).orElseThrow(() -> new UsernameNotFoundException("user not foun"+username));
  }
}
