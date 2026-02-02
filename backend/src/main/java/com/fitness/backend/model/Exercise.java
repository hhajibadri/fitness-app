package com.fitness.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Exercise {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message= "Name is required")
  @Size(max= 50, message= "Name must be at most 50 characters")
  private String name;

  @NotBlank(message= "Muscle group is required")
  @Size(max= 50, message= "Muscle group must be at most 50 characters")
  private String muscleGroups;
}
