package com.fitness.backend.dto;

import java.time.LocalDate;

import com.fitness.backend.enums.Gender;
import com.fitness.backend.validation.Adult;

import jakarta.validation.constraints.Positive;

public record UpdateUserProfileRequest(
    @Adult LocalDate dateOfBirth,
    Gender gender,

    @Positive Double height,

    @Positive Double weight,

    @Positive Double bodyFatPercentage) {
}