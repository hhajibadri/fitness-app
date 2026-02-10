package com.fitness.backend.model;

import java.util.Set;

import com.fitness.backend.enums.Muscle;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

  @Column(nullable = false)
  private String name;

  @ElementCollection(targetClass = Muscle.class)
  @Enumerated(EnumType.STRING)
  @CollectionTable(name = "exercise_muscles", joinColumns = @JoinColumn(name = "exercise_id"))
  private Set<Muscle> muscles;
}
