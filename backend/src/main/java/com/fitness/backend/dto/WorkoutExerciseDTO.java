package com.fitness.backend.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutExerciseDTO {

  private Long exerciseId;

  @NotEmpty(message = "Workout exercise must have at least 1 set")
  private List<SetEntryDTO> sets;
}
