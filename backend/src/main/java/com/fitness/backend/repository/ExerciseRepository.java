package com.fitness.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.backend.model.Exercise;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
  
  Optional<Exercise> findByName(String name);

  List<Exercise> findByNameContainingIgnoreCase(String partialName);

}
