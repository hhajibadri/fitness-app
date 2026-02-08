package com.movie.backend.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.backend.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{
  
  List<Movie> findGenresContaining(String genre);

  List<Movie> findByReleaseYear(int year);

  List<Movie> findByTitleContainingIgnoreCase(String keyword);

}
