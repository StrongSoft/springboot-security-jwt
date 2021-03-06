package com.example.springbootsecurityjwt.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {

  private String username;
  private String role;

  public AccountDTO(String username, String role) {
    super();
    this.username = username;
    this.role = role;
  }
}
