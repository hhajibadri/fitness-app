package com.fitness.backend.dto;

public record UserLoginResponse (
  long userId,
  String role,
  String accessToken,
  String tokenType,
  long accessExpiresInSeconds,
  String refreshToken,
  long refreshExpiresInSeconds,
  String issuedAtIso,
  UserProfileDetail userProfile
) {}
