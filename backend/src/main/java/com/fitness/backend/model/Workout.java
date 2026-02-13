package com.fitness.backend.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workout {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @JoinColumn(name = "user_id", nullable = false)
  @ManyToOne(optional = false)
  private User user;

  @Column(nullable = false)
  private LocalDateTime timestamp;

  @Builder.Default
  @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<WorkoutExercise> exercises = new ArrayList<>();

}

