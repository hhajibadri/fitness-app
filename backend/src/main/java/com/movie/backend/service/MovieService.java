package com.movie.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.movie.backend.model.Movie;
import com.movie.backend.repository.MovieRepository;

@Service
public class MovieService {

  private final MovieRepository movieRepository;

  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public List<Movie> getAllMovies() {
    return movieRepository.findAll();
  }

  public Movie getMovieById(Long id) {
    return movieRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Movie not found with id " + id));
  }

  public Movie createMovie(Movie movie) {
    return movieRepository.save(movie);
  }

  public Movie updateMovie(Movie movie) {
      return movieRepository.save(movie);
  }

  public void deleteMovie(Long id) {
    movieRepository.deleteById(id);
  }

  public List<Movie> filterByGenre(String genre) {
    return movieRepository.findAll().stream()
            .filter(m -> m.getGenres().contains(genre))
            .collect(Collectors.toList());
  }

  public List<Movie> filterByCast(String castMember) {
    return movieRepository.findAll().stream()
            .filter(m -> m.getCasts().contains(castMember))
            .collect(Collectors.toList());
  }

  public List<Movie> filterByReleaseYear(int year) {
    return movieRepository.findAll().stream()
            .filter(m -> m.getReleaseDate().getYear() == year)
            .collect(Collectors.toList());
  }
  
}
