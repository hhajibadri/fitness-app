package com.fitness.backend.exception;

public class UnauthorizedActionException extends RuntimeException {
  public UnauthorizedActionException() {
    super("Not authorized to perform this action");
  }
}
