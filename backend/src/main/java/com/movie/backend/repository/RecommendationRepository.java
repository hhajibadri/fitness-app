package com.movie.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.backend.model.Movie;
import com.movie.backend.model.Recommendation;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

  List<Recommendation> findByBaseMovieOrderBySimilarityScoreDesc(Movie baseMovie);

}
