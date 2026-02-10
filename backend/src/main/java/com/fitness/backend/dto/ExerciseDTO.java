package com.fitness.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseDTO {
  private Long id;
  private String name;
  private String muscleGroup;
}