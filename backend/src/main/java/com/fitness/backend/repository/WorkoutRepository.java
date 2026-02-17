package com.fitness.backend.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fitness.backend.model.Workout;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

  List<Workout> findByUserId(Long userId);

  List<Workout> findByCreatedAtAfter(Instant dateTime);

  Page<Workout> findByUserId(Long userId, Pageable pageable);

}
