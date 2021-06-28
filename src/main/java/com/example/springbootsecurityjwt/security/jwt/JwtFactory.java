package com.example.springbootsecurityjwt.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtFactory {

  private static final Logger LOG = LoggerFactory.getLogger(JwtFactory.class);

  public String generateToken(UserDetails userDetails) {
    String token = null;
    try {
      Set<String> roles = userDetails.getAuthorities().stream()
          .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
      String role = roles.iterator().next();

      token = JWT.create()
          .withIssuer("regur")
          .withClaim("USERNAME", userDetails.getUsername())
          .withClaim("USER_ROLE", role)
          .withClaim("EXP", new Date(System.currentTimeMillis() + 846000000))
          .sign(generateAlgorithm());
    } catch (Exception e) {
      LOG.error(e.getMessage());
    }
    return token;
  }

  private Algorithm generateAlgorithm() throws UnsupportedEncodingException {
    String signingKey = "jwttest";
    return Algorithm.HMAC256(signingKey);
  }
}
