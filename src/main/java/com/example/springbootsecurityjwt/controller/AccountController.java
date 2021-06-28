package com.example.springbootsecurityjwt.controller;

import com.example.springbootsecurityjwt.dto.AccountFromDTO;
import com.example.springbootsecurityjwt.entity.Account;
import com.example.springbootsecurityjwt.service.AccountService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

  private final AccountService accountService;

  @PostMapping
  public ResponseEntity<?> insertAccount(
      @Valid @RequestBody AccountFromDTO dto,
      BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Account accountDB = accountService.saveOrUpdateAccount(dto.toEntity());
    return new ResponseEntity<>(accountDB, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<?> viewAccount() {
    return new ResponseEntity<>("Success!", HttpStatus.OK);
  }
}
