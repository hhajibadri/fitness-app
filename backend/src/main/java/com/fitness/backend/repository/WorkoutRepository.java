package com.fitness.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fitness.backend.model.User;
import com.fitness.backend.model.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
  
  List<Workout> findByUser(User user);

  List<Workout> findByUserOrderByDateDesc(User user);

}
