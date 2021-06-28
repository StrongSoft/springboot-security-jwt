package com.example.springbootsecurityjwt.config;

import com.example.springbootsecurityjwt.constant.EnumMapper;
import com.example.springbootsecurityjwt.constant.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Order(1)
@Configuration
public class WebConfig {

  @Bean
  public EnumMapper enumMapper() {
    EnumMapper enumMapper = new EnumMapper();
    enumMapper.put("UserRole", UserRole.class);
    return enumMapper;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
