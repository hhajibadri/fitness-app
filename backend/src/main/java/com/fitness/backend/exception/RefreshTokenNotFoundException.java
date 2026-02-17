package com.fitness.backend.exception;

public class RefreshTokenNotFoundException extends RuntimeException {

  public RefreshTokenNotFoundException() {
    super("Refresh token has expired");
  }

}
