package com.fitness.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.backend.model.User;
import com.fitness.backend.model.Workout;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

  List<Workout> findByUser(User user);

  List<Workout> findByUserOrderByTimestampDesc(User user);

}
