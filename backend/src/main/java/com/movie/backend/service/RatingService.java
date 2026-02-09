package com.movie.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.backend.model.User;
import com.movie.backend.model.Movie;
import com.movie.backend.model.Rating;
import com.movie.backend.repository.RatingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

  @Autowired
  private RatingRepository ratingRepository;

  public List<Rating> findByUser(User user) {
    return ratingRepository.findByUser(user);
  }

  public List<Rating> findByMovie(Movie movie) {
    return ratingRepository.findByMovie(movie);
  }

  public Optional<Rating> findByUserAndMovie(User user, Movie movie) {
    return ratingRepository.findByUserAndMovie(user, movie);
  }

  public double averageMovieRating(Movie movie) {
    List<Rating> ratings = ratingRepository.findByMovie(movie);
    if (ratings.isEmpty()) {
      return 0.0;
    }
    double sumOfRatings = ratings.stream().mapToDouble(Rating::getScore).sum();
    return sumOfRatings / ratings.size();
  }
  
}
