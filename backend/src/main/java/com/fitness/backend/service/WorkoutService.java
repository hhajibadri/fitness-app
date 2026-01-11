package com.fitness.backend.service;

import org.springframework.stereotype.Service;

import com.fitness.backend.dto.SetEntryDTO;
import com.fitness.backend.dto.WorkoutDTO;
import com.fitness.backend.dto.WorkoutExerciseDTO;
import com.fitness.backend.model.Exercise;
import com.fitness.backend.model.SetEntry;
import com.fitness.backend.model.Workout;
import com.fitness.backend.model.WorkoutExercise;
import com.fitness.backend.repository.ExerciseRepository;
import com.fitness.backend.repository.WorkoutRepository;

import java.util.List;

@Service
public class WorkoutService {
  
  private final WorkoutRepository workoutRepository;
  private final ExerciseRepository exerciseRepository;

  public WorkoutService(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository) {
    this.workoutRepository = workoutRepository;
    this.exerciseRepository = exerciseRepository;
  }

  public Workout createWorkout(WorkoutDTO dto) {
    Workout workout = new Workout();
    workout.setName(dto.getName());
    workout.setDate(dto.getDate());

    for (WorkoutExerciseDTO exDto : dto.getExercises()) {
      Exercise exercise = exerciseRepository.findById(exDto.getExerciseId()).orElseThrow(() -> new RuntimeException("Exercise not found with id: " + exDto.getExerciseId()));

      WorkoutExercise we = new WorkoutExercise();
      we.setWorkout(workout);
      we.setExercise(exercise);

      for (SetEntryDTO setDto : exDto.getSets()) {
        SetEntry set = new SetEntry();
        set.setReps(setDto.getReps());
        set.setWeight(setDto.getWeight());
        set.setWorkoutExercise(we);
        we.getSets().add(set);
      }

      workout.getExercises().add(we);
    }

    return workoutRepository.save(workout);
  }

  public Workout getWorkoutById(Long id) {
    return workoutRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Workout not found with id: " + id));
  }

  public List<Workout> getAllWorkouts() {
    return workoutRepository.findAll();
  }
}
