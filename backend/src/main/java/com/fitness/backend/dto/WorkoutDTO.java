package com.fitness.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutDTO {

    @NotNull(message = "User id is required")
    private Long user_id;

    @NotBlank(message = "Workout must have name")
    private String name;

    @PastOrPresent(message = "Timestamp must not be in the future")
    private LocalDateTime timestamp;

    @NotEmpty(message = "Must have at least one workout exercise")
    private List<WorkoutExerciseDTO> exercises;
}
