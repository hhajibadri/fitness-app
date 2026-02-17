package com.fitness.backend.security;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fitness.backend.model.User;

import io.jsonwebtoken.Claims;
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

  private final Duration jwtDuration = Duration.ofMinutes(15);

  private SecretKey key;

  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(User user) {
    Instant currentTime = Instant.now();
    Instant expirationTime = currentTime.plus(jwtDuration);
    return Jwts
        .builder()
        .subject(user.getEmail())
        .claim("id", user.getId())
        .claim("role", user.getRole())
        .issuedAt(java.util.Date.from(currentTime))
        .expiration(java.util.Date.from(expirationTime))
        .signWith(key)
        .compact();
  }

  public String getEmailFromToken(String token) {
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

  public Instant getExpiry(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload();
    return claims.getExpiration().toInstant();
  }

  public Instant getIssuedAt(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload();
    return claims.getIssuedAt().toInstant();
  }

}
