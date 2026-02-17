package com.fitness.backend.dto;

public record RefreshResponse (
  String accessToken,
  String refreshToken,
  long expiresInSecondsAccess,
  long expiresInSecondsRefresh
) {}
