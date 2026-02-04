package com.security.basic.repo;

import com.security.basic.entity.Otp;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepo extends JpaRepository<Otp,Integer> {
  Optional<Otp> getOtpByUserName(String userName);
}
