package com.fitness.backend.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fitness.backend.exception.RefreshTokenExpiredException;
import com.fitness.backend.exception.RefreshTokenNotFoundException;
import com.fitness.backend.model.RefreshToken;
import com.fitness.backend.model.User;
import com.fitness.backend.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;
  private final Duration refreshExpiration = Duration.ofDays(7);

  public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
  }

  public RefreshToken createRefreshToken(User user) {
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setUser(user);
    refreshToken.setExpiryInstant(Instant.now().plus(refreshExpiration));
    String plainToken = UUID.randomUUID().toString();
    String tokenHash = hashToken(plainToken);
    refreshToken.setToken(tokenHash);
    return refreshTokenRepository.save(refreshToken);
  }

  public RefreshToken getByToken(String token) {
    return refreshTokenRepository.findByToken(token)
        .orElseThrow(() -> new RefreshTokenNotFoundException());
  }

  public RefreshToken verifyRefreshToken(RefreshToken refreshToken) {

    if (refreshToken.getExpiryInstant().isBefore(Instant.now())) {
      refreshTokenRepository.delete(refreshToken);
      throw new RefreshTokenExpiredException();
    }
    return refreshToken;
  }

  public RefreshToken rotateRefreshToken(RefreshToken oldToken) {
    User user = oldToken.getUser();
    refreshTokenRepository.delete(oldToken);
    return createRefreshToken(user);
  }

  public void deleteByUser(User user) {
    refreshTokenRepository.deleteByUser(user);
  }

  @Scheduled(cron = "0 0 0 * * ?")
  public void purgeExpiredTokens() {
    refreshTokenRepository.deleteAllByExpiryInstantBefore(Instant.now());
  }

  public String hashToken(String token) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(hash);
    } catch(NoSuchAlgorithmException e) {
      throw new RuntimeException("SHA-256 algorithm not available", e);
    }
  }

}
