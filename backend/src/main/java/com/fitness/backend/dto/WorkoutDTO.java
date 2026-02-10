package com.fitness.backend.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutDTO {
    private String name;
    private LocalDate date;
    private List<WorkoutExerciseDTO> exercises;
}
