package com.fitness.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitness.backend.model.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
  
}
