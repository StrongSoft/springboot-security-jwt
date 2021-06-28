package com.example.springbootsecurityjwt.security.token;

import com.example.springbootsecurityjwt.dto.AccountFromDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthenticatedToken extends UsernamePasswordAuthenticationToken {

  public PreAuthenticatedToken(String username, String password) {
    super(username, password);
  }

  public PreAuthenticatedToken(AccountFromDTO dto) {
    this(dto.getUsername(), dto.getPassword());
  }

  public String getUsername() {
    return (String) super.getPrincipal();
  }

  public String getUserPassword() {
    return (String) super.getCredentials();
  }
}
