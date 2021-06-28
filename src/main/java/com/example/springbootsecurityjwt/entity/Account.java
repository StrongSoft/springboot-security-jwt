package com.example.springbootsecurityjwt.entity;

import com.example.springbootsecurityjwt.constant.UserRole;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account extends BaseEntity {

  @Column(nullable = false, unique = true)
  public String username;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private UserRole role;

  public void encodePassword(PasswordEncoder passwordEncoder) {
    password = passwordEncoder.encode(password);
  }

  @Builder
  public Account(String username, String password, UserRole role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }
}
