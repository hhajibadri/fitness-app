package com.fitness.backend.dto;

import java.util.Set;

import com.fitness.backend.enums.Muscle;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ExerciseDTO (
  Long id,

  @NotBlank(message = "Exercise must have name")
  @Size(max = 50, message = "Name must not exceed 50 characters")
  String name,

  @NotEmpty(message = "Must have at least one muscle")
  Set<Muscle> muscles
) {}