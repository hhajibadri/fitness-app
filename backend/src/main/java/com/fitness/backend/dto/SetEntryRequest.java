package com.fitness.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record SetEntryRequest (

  @Min(value = 1, message = "Reps must be positive")
  @Max(value = 100, message = "Reps must not exceed 100")
  int reps,

  @Positive(message = "Weight must be positive")
  double weight
) {}
