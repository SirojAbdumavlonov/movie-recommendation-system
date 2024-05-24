package com.app.movie.service;

import com.app.movie.domain.Movie;
import com.app.movie.dto.MovieSearchCriteriaDTO;
import com.app.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<Movie> getAll() {
        log.debug("Getting all movies");
        List<Movie> movies = movieRepository.findAll();
        log.debug("Found movies: {}", movies);
        return movies;
    }

    public List<Movie> searchMovies(MovieSearchCriteriaDTO movieSearchCriteriaDTO) {
        log.debug("Searching movies by criteria: {}", movieSearchCriteriaDTO);
        List<Movie> movies = movieRepository.searchMovies(movieSearchCriteriaDTO);
        log.debug("Found movies: {}", movieSearchCriteriaDTO);
        return movies;
    }

    public Optional<Movie> getById(Long id) {
        log.debug("Getting movie by id: {}", id);
        Optional<Movie> movie = movieRepository.findById(id);
        log.debug("Found movie: {}", movie);
        return movie;
    }

    public List<Movie> getSimilarMovies(Long movieId) {
        log.debug("Getting similar movies: {}", movieId);
        List<Movie> similarMovies = movieRepository.findSimilar();

        if(similarMovies.isEmpty()) {
            log.warn("Similar movies not found. MovieId: {}", movieId);
            return similarMovies;
        }

        log.debug("Found similar movies: {}", similarMovies);
        return similarMovies;
    }
}
