package com.fitness.backend.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<Map<String, String>> handleInvalidCredentials() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(Map.of("error", "Invalid credentials"));
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleUserNotFound() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Map.of("error", "User not found"));
  }

  @ExceptionHandler(EmailAlreadyInUseException.class)
  public ResponseEntity<Map<String, String>> handleEmailAlreadyInUse() {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(Map.of("error", "Email already in use"));
  }

  @ExceptionHandler(RefreshTokenExpiredException.class)
  public ResponseEntity<Map<String, String>> handleExpiredToken() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(Map.of("error", "Refresh token expired"));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleOtherExceptions(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Map.of("error", ex.getMessage()));
  }

  @ExceptionHandler(ExerciseNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleExerciseNotFound() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Map.of("error", "Exercise not found"));
  }
}
