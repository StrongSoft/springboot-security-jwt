package com.example.springbootsecurityjwt.security.filter;

import com.example.springbootsecurityjwt.dto.AccountFromDTO;
import com.example.springbootsecurityjwt.security.token.PreAuthenticatedToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class FormLoginFilter extends AbstractAuthenticationProcessingFilter {

  private AuthenticationSuccessHandler authenticationSuccessHandler;
  private AuthenticationFailureHandler authenticationFailureHandler;

  protected FormLoginFilter(String defaultFilterProcessesUrl) {
    super(defaultFilterProcessesUrl);
  }
  
  public FormLoginFilter(
      String defaultUrl,
      AuthenticationSuccessHandler successHandler,
      AuthenticationFailureHandler failureHandler) {
    super(defaultUrl);
    this.authenticationSuccessHandler = successHandler;
    this.authenticationFailureHandler = failureHandler;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {
    // JSON으로 변환
    AccountFromDTO dto = new ObjectMapper().readValue(
        request.getReader(),
        AccountFromDTO.class);

    // 사용자입렧값이 존재하는지 비교
    PreAuthenticatedToken token = new PreAuthenticatedToken(dto);

    // PreAuthenticatedToken 해당 객체에 맞는 Provider를
    // getAuthenticationManger 해당 메서드가 자종으로 찾아서 연결해 준다.
    // 자동으로 찾아준다고 해도 Provider 에 직접 PreAuthenticatedToken 지정해 줘야 찾아갑니다.
    return super
        .getAuthenticationManager()
        .authenticate(token);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    authenticationSuccessHandler
        .onAuthenticationSuccess(request, response, authResult);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    authenticationFailureHandler
        .onAuthenticationFailure(request, response, failed);
  }
}
