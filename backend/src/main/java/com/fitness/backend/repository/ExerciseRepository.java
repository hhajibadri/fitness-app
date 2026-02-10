package com.fitness.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitness.backend.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
  
}
