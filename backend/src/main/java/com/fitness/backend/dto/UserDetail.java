package com.fitness.backend.dto;

public record UserDetail (
  Long userId,
  String role,
  String accessToken,
  Long accessExpiresInSeconds,
  String refreshToken,
  Long refreshExpiresInSeconds,
  String issuedAtIso,
  UserProfileDetail userProfile
) {}
