package com.fitness.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record WorkoutRequest (

    @NotNull(message = "User id is required")
    Long user_id,

    @NotBlank(message = "Workout must have name")
    String name,

    @PastOrPresent(message = "Timestamp must not be in the future")
    LocalDateTime timestamp,

    @NotEmpty(message = "Must have at least one workout exercise")
    List<WorkoutExerciseRequest> exercises
) {}
