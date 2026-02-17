package com.fitness.backend.exception;

public class ExerciseNotFoundException extends RuntimeException {

  public ExerciseNotFoundException() {
    super("Exercise not found");
  }
  
}
