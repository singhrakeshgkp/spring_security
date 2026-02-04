package com.security.basic.repo;

import com.security.basic.entity.Otp;
import com.security.basic.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Customer,Integer> {

  Optional<Customer> findUserByUserName(String userName);
}
