package com.fitness.backend.service;

import java.time.Duration;
import java.time.Instant;
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
    RefreshToken token = new RefreshToken();
    token.setUser(user);
    token.setToken(UUID.randomUUID().toString());
    token.setExpiryInstant(Instant.now().plus(refreshExpiration));
    return refreshTokenRepository.save(token);
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

}
