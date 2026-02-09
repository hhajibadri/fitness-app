package com.movie.backend.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponseDTO {

  private Long id;
  private String title;
  private String description;
  private Set<String> genres;
  private Set<String> cast;
  private LocalDate releaseDate;
  
}
