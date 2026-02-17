package com.fitness.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest (

  @NotBlank(message = "Must enter email")
  @Email(message = "Email must have valid format")
  String email,

  @NotBlank(message = "Must enter password")
  String password

) {}
