package com.movie.backend.dto;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovieRequestDTO {

  @NotBlank(message = "Title is required")
  private String title;

  @NotBlank(message = "Description is required")
  private String description;

  @NotEmpty(message = "Must have at least one genre")
  private Set<String> genres;

  @NotEmpty(message = "Must have at least one cast member")
  private Set<String> cast;

  @NotNull(message = "Release date is required")
  private LocalDate releaseDate;
  
}
