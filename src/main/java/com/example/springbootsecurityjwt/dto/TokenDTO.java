package com.example.springbootsecurityjwt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenDTO {

  @JsonInclude(Include.NON_NULL)
  private String token;
  @JsonInclude(Include.NON_NULL)
  private String username;

  public TokenDTO(String token, String username) {
    this.token = token;
    this.username = username;
  }
}
