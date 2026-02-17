package com.fitness.backend.security;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fitness.backend.model.User;
import com.fitness.backend.service.UserService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserService userService;

  public JwtFilter(JwtUtil jwtUtil, UserService userService) {
    this.jwtUtil = jwtUtil;
    this.userService = userService;
  }

  @Override
  public void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
      FilterChain filterChain) throws ServletException, IOException {

    Cookie[] cookies = servletRequest.getCookies();

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("access_token")) {
          String token = cookie.getValue();
          if (jwtUtil.validateJwtToken(token)) {
            String email = jwtUtil.getEmailFromToken(token);
            User user = userService.getUserByEmail(email);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                user, null, List.of(new SimpleGrantedAuthority(user.getRole().name())));
            SecurityContextHolder.getContext().setAuthentication(auth);
          }
          break;
        }
      }
    }

  }

}
