package com.example.springbootsecurityjwt.service;

import com.example.springbootsecurityjwt.entity.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AccountService extends UserDetailsService {

  @Override
  UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

  Account saveOrUpdateAccount(Account account);
}
