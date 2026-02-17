package com.fitness.backend.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fitness.backend.model.Exercise;
import com.fitness.backend.repository.ExerciseRepository;

@Service
public class ExerciseService {

  private final ExerciseRepository exerciseRepository;

  public ExerciseService(ExerciseRepository exerciseRepository) {
    this.exerciseRepository = exerciseRepository;
  }

  List<Exercise> getAllExercises() {
    return exerciseRepository.findAll(Sort.by("exerciseName").ascending());
  }

}
