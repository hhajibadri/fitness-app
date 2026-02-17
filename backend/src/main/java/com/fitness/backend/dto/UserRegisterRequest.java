package com.fitness.backend.dto;

import java.time.LocalDate;

import com.fitness.backend.enums.Gender;
import com.fitness.backend.validation.Adult;
import com.fitness.backend.validation.Password;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UserRegisterRequest (

  String name,

  @NotBlank(message = "Must enter email")
  @Email(message = "Email must have valid format")
  String email,

  @NotBlank(message = "Must enter password")
  @Password
  String password,

  @NotNull(message = "Must provide date of birth")
  @Adult
  LocalDate dateOfBirth,

  Gender gender,

  @Positive(message = "Height must be positive")
  @NotNull(message = "Must provide height")
  Double height,

  @Positive(message = "Weight must be positive")
  @NotNull(message = "Must provide weight")
  Double weight,

  @Positive(message = "Bodyfat must be positive")
  @NotNull(message = "Must provide body fat percentage")
  Double bodyFatPercentage
) {}
