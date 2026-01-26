package com.security.basic.repo;

import com.security.basic.entity.Customer;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepo extends CrudRepository<Customer,Integer> {
  Optional<Customer> findByUserName(String userName);
}
