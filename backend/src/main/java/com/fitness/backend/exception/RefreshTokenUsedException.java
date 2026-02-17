package com.fitness.backend.exception;

public class RefreshTokenUsedException extends RuntimeException {
  
  public RefreshTokenUsedException() {
    super("Refresh token has already been used");
  }

}
