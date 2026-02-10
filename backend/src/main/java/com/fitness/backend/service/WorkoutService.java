package com.fitness.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fitness.backend.dto.SetEntryDTO;
import com.fitness.backend.dto.WorkoutDTO;
import com.fitness.backend.dto.WorkoutExerciseDTO;
import com.fitness.backend.model.Exercise;
import com.fitness.backend.model.SetEntry;
import com.fitness.backend.model.User;
import com.fitness.backend.model.Workout;
import com.fitness.backend.model.WorkoutExercise;
import com.fitness.backend.repository.ExerciseRepository;
import com.fitness.backend.repository.UserRepository;
import com.fitness.backend.repository.WorkoutRepository;

@Service
public class WorkoutService {
  
  private final WorkoutRepository workoutRepository;
  private final ExerciseRepository exerciseRepository;
  private final UserRepository userRepository;

  public WorkoutService(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository, UserRepository userRepository) {
    this.workoutRepository = workoutRepository;
    this.exerciseRepository = exerciseRepository;
    this.userRepository = userRepository;
  }

  public Workout createWorkout(WorkoutDTO workoutDTO) {

    User user = userRepository.findById(workoutDTO.getUser_id())
              .orElseThrow(() -> new RuntimeException("User not found"));

    Workout workout = new Workout();
    workout.setName(workoutDTO.getName());
    workout.setTimestamp(LocalDateTime.now());
    workout.setUser(user);

    for (WorkoutExerciseDTO exerciseDTO : workoutDTO.getExercises()) {
      Exercise exercise = exerciseRepository.findById(exerciseDTO.getExerciseId()).orElseThrow(() -> new RuntimeException("Exercise not found with id: " + exerciseDTO.getExerciseId()));

      WorkoutExercise workoutExercise = new WorkoutExercise();
      workoutExercise.setWorkout(workout);
      workoutExercise.setExercise(exercise);

      for (SetEntryDTO setEntryDTO : exerciseDTO.getSets()) {
        SetEntry setEntry = new SetEntry();
        setEntry.setReps(setEntryDTO.getReps());
        setEntryDTO.setWeight(setEntryDTO.getWeight());
        setEntry.setWorkoutExercise(workoutExercise);
        workoutExercise.getSets().add(setEntry);
      }

      workout.getExercises().add(workoutExercise);
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
