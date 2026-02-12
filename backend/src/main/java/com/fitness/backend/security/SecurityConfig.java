package com.fitness.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.fitness.backend.service.UserService;

@Configuration
public class SecurityConfig {

  private final UserService userService;
  private final JwtUtil jwtUtil;

  public SecurityConfig(UserService userService, JwtUtil jwtUtil) {
    this.userService = userService;
    this.jwtUtil = jwtUtil;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    JwtFilter jwtFilter = new JwtFilter(jwtUtil, userService);
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth.requestMatchers("/api/users/login", "/api/users/register").permitAll()
            .anyRequest().authenticated())
        .addFilterBefore(jwtFilter,
            org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

}
