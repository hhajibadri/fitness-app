package com.fitness.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fitness.backend.dto.SetEntryRequest;
import com.fitness.backend.dto.WorkoutExerciseRequest;
import com.fitness.backend.dto.WorkoutRequest;
import com.fitness.backend.exception.ExerciseNotFoundException;
import com.fitness.backend.exception.UserNotFoundException;
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

  private final ExerciseRepository exerciseRepository;
  private final UserRepository userRepository;
  private final WorkoutRepository workoutRepository;

  public WorkoutService(ExerciseRepository exerciseRepository, UserRepository userRepository,
      WorkoutRepository workoutRepository) {
    this.exerciseRepository = exerciseRepository;
    this.userRepository = userRepository;
    this.workoutRepository = workoutRepository;
  }

  public void createWorkout(WorkoutRequest workoutRequest) {

    User user = userRepository.findById(workoutRequest.user_id()).orElseThrow(() -> new UserNotFoundException());

    Workout workout = new Workout();
    workout.setName(workoutRequest.name());
    workout.setTimestamp(LocalDateTime.now());
    workout.setUser(user);

    for (WorkoutExerciseRequest workoutExerciseRequest : workoutRequest.exercises()) {

      Exercise exercise = exerciseRepository.findById(workoutExerciseRequest.exerciseId())
          .orElseThrow(() -> new ExerciseNotFoundException());

      WorkoutExercise workoutExercise = new WorkoutExercise();
      workoutExercise.setWorkout(workout);
      workoutExercise.setExercise(exercise);

      for (SetEntryRequest setEntryRequest : workoutExerciseRequest.sets()) {

        SetEntry setEntry = new SetEntry();
        setEntry.setReps(setEntryRequest.reps());
        setEntry.setWeight(setEntryRequest.weight());

        workoutExercise.getSets().add(setEntry);

      }

      workout.getWorkoutExercises().add(workoutExercise);

    }

  }

  public void deleteWorkout(Long id) {
    workoutRepository.deleteById(id);
  }

  public Page<Workout> getWorkoutsPaged(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return workoutRepository.findAll(pageable);
  }

  public List<Workout> getAllWorkouts() {
    return workoutRepository.findAll();
  }

}
