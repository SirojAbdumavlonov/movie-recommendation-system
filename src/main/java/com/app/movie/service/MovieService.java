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

// TODO:
// 1. recommend genre and user ratings
// 2. top rated-movies on their preferred genre on a specific year
// 3. adding movies into watchlist for later viewing
// 4. allow users to search for movies by specific criteria such as genre, release year, or director
// 5. implement a feature to filter out movies that a user has already watched or expressed disinterest in

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
