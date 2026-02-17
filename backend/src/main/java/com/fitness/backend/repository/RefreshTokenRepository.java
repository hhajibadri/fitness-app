package com.fitness.backend.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitness.backend.model.RefreshToken;
import com.fitness.backend.model.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByToken(String token);

  void deleteAllByExpiryInstantBefore(Instant now);

  void deleteByUser(User user);
}
