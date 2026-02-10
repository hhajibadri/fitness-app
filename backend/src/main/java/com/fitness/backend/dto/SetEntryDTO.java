package com.fitness.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetEntryDTO {

  @Min(value = 1, message = "Reps must be positive")
  @Max(value = 100, message = "Reps must not exceed 100")
  private int reps;

  @Positive(message = "Weight must be positive")
  private double weight;
}
