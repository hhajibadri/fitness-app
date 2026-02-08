package com.movie.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {
  
  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private Movie baseMovie;

  @ManyToOne
  private Movie recommendedMovie;

  private double similarityScore;

}
