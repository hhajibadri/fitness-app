package com.fitness.backend.exception;

public class UserNotFoundException extends RuntimeException {
  
  public UserNotFoundException() {
    super("User not found");
  }

}
