package com.example.springbootsecurityjwt.repository;

import com.example.springbootsecurityjwt.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

  Optional<Account> findByUsername(String username);
}
