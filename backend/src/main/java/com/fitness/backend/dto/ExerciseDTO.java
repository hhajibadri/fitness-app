package com.fitness.backend.dto;

import java.util.Set;

import com.fitness.backend.enums.Muscle;
import com.fitness.backend.model.Exercise;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseDTO {
  private Long id;

  @NotBlank(message = "Exercise must have name")
  @Size(max = 50, message = "Name must not exceed 50 characters")
  private String name;

  @NotEmpty(message = "Must have at least one muscle")
  private Set<Muscle> muscles;

  public ExerciseDTO(Exercise exercise) {
    this.id = exercise.getId();
    this.name = exercise.getName();
    this.muscles = exercise.getMuscles();
  }
}