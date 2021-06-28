package com.example.springbootsecurityjwt.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.example.springbootsecurityjwt.constant.UserRole;
import com.example.springbootsecurityjwt.entity.Account;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AccountRepositoryTest {

  @Autowired
  private AccountRepository accountRepository;

  @AfterEach
  public void cleanup() {
    accountRepository.deleteAll();
  }

  @Test
  public void userInsert() {
    //given
    accountRepository.save(Account.builder()
        .username("regur")
        .password("1234")
        .role(UserRole.USER)
        .build());

    //when
    List<Account> accounts = accountRepository.findAll();

    //then
    Account account = accounts.get(0);
    assertThat(account.getUsername(), is("regur"));
    assertThat(account.getPassword(), is("1234"));
    assertThat(account.getRole(), is(UserRole.USER));
  }
}