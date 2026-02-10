package com.fitness.backend.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutExerciseDTO {
  private Long exerciseId;
  private List<SetEntryDTO> sets;
}
