package com.movie.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.backend.model.Movie;
import com.movie.backend.model.Rating;
import com.movie.backend.model.User;

public interface RatingRepository extends JpaRepository<Rating, Long> {

  List<Rating> findByUser(User user);

  List<Rating> findByMovie(Movie movie);

  Optional<Rating> findByUserAndMovie(User user, Movie movie);
  
}
