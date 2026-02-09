package com.movie.backend.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration}")
  private long jwtExpirationMs;

  private SecretKey key;

  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String email) {
    Date currentTime = new Date();
    Date expirationTime = new Date(currentTime.getTime() + jwtExpirationMs);
    return Jwts
            .builder()
            .subject(email)
            .issuedAt(currentTime)
            .expiration(expirationTime)
            .signWith(key)
            .compact();
  }

  public String getUserFromToken(String token) {
    return Jwts
            .parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
  }

  public boolean validateJwtToken(String token) {
    try {
        Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        return true;
    } catch (JwtException | IllegalArgumentException e) {
      log.error("JWT validation error: {}", e.getMessage());
    }
    return false;
  }

}
