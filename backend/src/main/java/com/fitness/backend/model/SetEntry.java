package com.fitness.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class SetEntry {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message= "Reps are required")
  @Min(value = 1, message= "Reps must be at least 1")
  private int reps;

  @NotNull(message= "Weight is required")
  @Positive(message = "Weight must be positive")
  private double weight;

  @ManyToOne
  @JoinColumn(name = "workout_exercise_id")
  private WorkoutExercise workoutExercise;
}
