package com.example.springbootsecurityjwt.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtPreProcessingToken extends UsernamePasswordAuthenticationToken {

  public JwtPreProcessingToken(Object principal, Object credentials) {
    super(principal, credentials);
  }

  public JwtPreProcessingToken(String token) {
    this(token, token.length());
  }
}
