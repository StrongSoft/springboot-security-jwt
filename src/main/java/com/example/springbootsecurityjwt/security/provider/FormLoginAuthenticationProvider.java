package com.example.springbootsecurityjwt.security.provider;

import com.example.springbootsecurityjwt.security.token.PostAuthorizationToken;
import com.example.springbootsecurityjwt.security.token.PreAuthenticatedToken;
import com.example.springbootsecurityjwt.service.AccountService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

  private final AccountService accountService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    PreAuthenticatedToken token = (PreAuthenticatedToken) authentication;

    String username = token.getUsername();
    String password = token.getUserPassword();

    UserDetails accountDB = accountService.loadUserByUsername(username);

    // 비밀번호 체크
    if (isCorrectPassword(password, accountDB.getPassword())) {
      return PostAuthorizationToken.getTokenFormUserDetails(accountDB);
    }

    //이곳까지 통과하지 못하면 잘못된 요청으로 접근하지 못한것 그러므로 throw 해줘야 한다.
    throw new NoSuchElementException("인증 정보가 정확하지 않습니다.");
  }

  @Override
  // Provider 를 연결 해주는 메소드 PreAuthorizationToken 사용한 filter 를 검색 후 연결
  public boolean supports(Class<?> authentication) {
    return PreAuthenticatedToken.class.isAssignableFrom(authentication);
  }

  private boolean isCorrectPassword(String password, String accountPassword) {
    return passwordEncoder.matches(password, accountPassword);
  }
}
