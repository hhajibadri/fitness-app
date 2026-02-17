package com.fitness.backend.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public record WorkoutExerciseRequest (

  Long exerciseId,

  @NotEmpty(message = "Workout exercise must have at least 1 set")
  List<SetEntryRequest> sets
) {}
