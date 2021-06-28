package com.example.springbootsecurityjwt.security.token;

import com.example.springbootsecurityjwt.dto.AccountDTO;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {

  public PostAuthorizationToken(Object principal, Object credentials,
      Collection<? extends GrantedAuthority> authorities) {
    super(principal, credentials, authorities);
  }

  public static PostAuthorizationToken getTokenFormUserDetails(UserDetails userDetails) {
    return new PostAuthorizationToken(
        userDetails,
        userDetails.getPassword(),
        userDetails.getAuthorities()
    );
  }

  public UserDetails getUserDetails() {
    return (UserDetails) super.getPrincipal();
  }

  public static PostAuthorizationToken getTokenFormUserDetails(AccountDTO accountDTO) {
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    grantedAuthorities.add(new SimpleGrantedAuthority(accountDTO.getRole()));

    return new PostAuthorizationToken(
        accountDTO,
        "null password",
        grantedAuthorities
    );
  }
}
