package com.fitness.backend.exception;

public class EmailAlreadyInUseException extends RuntimeException {
  
  public EmailAlreadyInUseException() {
    super("Email already in use");
  }

}
