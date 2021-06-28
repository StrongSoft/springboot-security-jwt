package com.example.springbootsecurityjwt.security.handler;

import com.example.springbootsecurityjwt.dto.TokenDTO;
import com.example.springbootsecurityjwt.security.jwt.JwtFactory;
import com.example.springbootsecurityjwt.security.token.PostAuthorizationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtFactory factory;
  private final ObjectMapper objectMapper;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
      HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    PostAuthorizationToken token = (PostAuthorizationToken) authentication;
    UserDetails userDetails = token.getUserDetails();

    String tokenString = factory.generateToken(userDetails);

    TokenDTO tokenDTO = new TokenDTO(tokenString, userDetails.getUsername());
    processResponse(response, tokenDTO);
  }

  private void processResponse(HttpServletResponse response, TokenDTO dto)
      throws IOException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.OK.value());
    response.getWriter().write(objectMapper.writeValueAsString(dto));
  }
}
