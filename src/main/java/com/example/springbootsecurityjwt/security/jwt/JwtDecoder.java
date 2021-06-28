package com.example.springbootsecurityjwt.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springbootsecurityjwt.dto.AccountDTO;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JwtDecoder {

  private final Logger LOG = LoggerFactory.getLogger(getClass());

  public AccountDTO decodeJwt(String token) {
    DecodedJWT decodedJWT = isValidToken(token)
        .orElseThrow(() -> new NoSuchElementException("유효한 토큰이 아닙니다."));

    String username = decodedJWT
        .getClaim("USERNAME")
        .asString();
    String role = decodedJWT
        .getClaim("USER_ROLE")
        .asString();

    return new AccountDTO(username, role);
  }

  private Optional<DecodedJWT> isValidToken(String token) {
    DecodedJWT jwt = null;

    try {
      Algorithm algorithm = Algorithm.HMAC256("jwttest");
      JWTVerifier verifier = JWT
          .require(algorithm)
          .build();

      jwt = verifier.verify(token);
    } catch (Exception e) {
      LOG.error(e.getMessage());
    }

    return Optional.ofNullable(jwt);
  }

}
