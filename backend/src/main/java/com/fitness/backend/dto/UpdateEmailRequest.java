package com.fitness.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateEmailRequest(
  @NotBlank(message = "Must provide email")
  String newEmail,
  String password
) {}
