package com.fitness.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.backend.enums.ExerciseName;
import com.fitness.backend.model.Exercise;



@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
  
  List<Exercise> findByExerciseName(ExerciseName exerciseName);

}
