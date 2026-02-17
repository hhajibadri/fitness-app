package com.fitness.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest (

  @NotBlank(message = "Must provide token")
  String token

) {}
