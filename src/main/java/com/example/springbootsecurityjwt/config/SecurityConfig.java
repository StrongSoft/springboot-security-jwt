package com.example.springbootsecurityjwt.config;

import com.example.springbootsecurityjwt.security.common.FilterSkipMatcher;
import com.example.springbootsecurityjwt.security.filter.FormLoginFilter;
import com.example.springbootsecurityjwt.security.filter.JwtAuthenticationFilter;
import com.example.springbootsecurityjwt.security.handler.FormLoginAuthenticationFailureHandler;
import com.example.springbootsecurityjwt.security.handler.FormLoginAuthenticationSuccessHandler;
import com.example.springbootsecurityjwt.security.handler.HeaderTokenExtractor;
import com.example.springbootsecurityjwt.security.provider.FormLoginAuthenticationProvider;
import com.example.springbootsecurityjwt.security.provider.JWTAuthenticationProvider;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final FormLoginAuthenticationSuccessHandler formLoginAuthenticationSuccessHandler;
  private final FormLoginAuthenticationFailureHandler formLoginAuthenticationFailureHandler;
  private final JWTAuthenticationProvider jwtProvider;
  private final FormLoginAuthenticationProvider provider;
  private final HeaderTokenExtractor headerTokenExtractor;

  @Bean
  public AuthenticationManager getAuthenticationManager() throws Exception {
    return super.authenticationManagerBean();
  }

  protected FormLoginFilter formLoginFilter() throws Exception {
    FormLoginFilter filter = new FormLoginFilter(
        "/api/account/login",
        formLoginAuthenticationSuccessHandler,
        formLoginAuthenticationFailureHandler
    );
    filter.setAuthenticationManager(super.authenticationManagerBean());
    return filter;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(provider);
    auth.authenticationProvider(jwtProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    //초기화면 비활성화
    http.csrf().disable();
    http.headers().frameOptions().disable();
    http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(
        formLoginFilter(),
        UsernamePasswordAuthenticationFilter.class
    )
        .addFilterBefore(
            jwtFilter(),
            UsernamePasswordAuthenticationFilter.class
        );
    http
        .authorizeRequests()
        .mvcMatchers(
            HttpMethod.GET,
            "/api/account"
        )
        .hasRole("USER");
  }

  private JwtAuthenticationFilter jwtFilter() throws Exception {
    List<String> skipPath = new ArrayList<>();

    // Static 정보 접근 허용
    skipPath.add("GET,/error");
    skipPath.add("GET,/favicon.ico");
    skipPath.add("GET,/static");
    skipPath.add("GET,/static/**");

    skipPath.add("POST,/api/account");
    skipPath.add("POST,/api/account/login");

    FilterSkipMatcher matcher = new FilterSkipMatcher(
        skipPath,
        "/**"
    );

    JwtAuthenticationFilter filter = new JwtAuthenticationFilter(
        matcher,
        headerTokenExtractor
    );
    filter.setAuthenticationManager(super.authenticationManagerBean());

    return filter;
  }
}
