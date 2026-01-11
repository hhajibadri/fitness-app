package com.fitness.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.backend.dto.WorkoutDTO;
import com.fitness.backend.service.WorkoutService;
import com.fitness.backend.model.Workout;

import java.util.List;


@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {
  
  private final WorkoutService workoutService;

  public WorkoutController(WorkoutService workoutService) {
    this.workoutService = workoutService;
  }

  @PostMapping
  public ResponseEntity<Workout> createWorkout(@RequestBody WorkoutDTO workoutDTO) {
      Workout workout = workoutService.createWorkout(workoutDTO);
      return ResponseEntity.ok(workout);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Workout> getWorkout(@PathVariable Long id) {
    Workout workout = workoutService.getWorkoutById(id);
    return ResponseEntity.ok(workout);
  }

  @GetMapping
  public ResponseEntity<List<Workout>> getAllWorkouts() {
    List<Workout> workouts = workoutService.getAllWorkouts();
    return ResponseEntity.ok(workouts);
  }
}
