package com.movie.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.backend.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long>{

  List<Movie> findByTitleContainingIgnoreCase(String keyword);

  List<Movie> findTop10ByOrderByReleaseDateDesc();

}
