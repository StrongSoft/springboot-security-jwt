package com.example.springbootsecurityjwt.controller;

import com.example.springbootsecurityjwt.constant.EnumMapper;
import com.example.springbootsecurityjwt.constant.EnumValue;
import com.example.springbootsecurityjwt.constant.UserRole;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EnumController {

  private final EnumMapper enumMapper;

  @GetMapping("/enum")
  public Map<String, Object> getEnum() {
    Map<String, Object> enums = new LinkedHashMap<>();
    Class<UserRole> userRole = UserRole.class;
    enums.put("userRole", userRole.getEnumConstants());
    return enums;
  }

  @GetMapping("/mapper")
  public Map<String, List<EnumValue>> getEnumValue() {
    return enumMapper.getAll();
  }
}
