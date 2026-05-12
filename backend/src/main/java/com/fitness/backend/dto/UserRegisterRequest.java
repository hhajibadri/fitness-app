package com.fitness.backend.dto;

import com.fitness.backend.enums.Gender;
import com.fitness.backend.validation.Adult;
import com.fitness.backend.validation.Password;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
  String dateOfBirth,

  @NotNull(message = "Must provide gender")
  Gender gender
) {}
