package com.fitness.backend.exception;

public class RefreshTokenExpiredException extends RuntimeException {
  public RefreshTokenExpiredException() {
    super("Refresh token is expired");
  }
}
