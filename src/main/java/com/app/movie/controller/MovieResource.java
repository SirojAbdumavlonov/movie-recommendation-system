package com.app.movie.controller;

import com.app.movie.domain.Movie;
import com.app.movie.dto.MovieSearchCriteriaDTO;
import com.app.movie.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Transactional
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MovieResource {

    // TODO:
    // 1. recommend genre and user ratings
    // top rated-movies on their preferred genre on a specific year
    // adding movies into watchlist for later viewing
    // allow users to search for movies by specific criteria such as genre, release year, or director
    // implement a feature to filter out movies that a user has already watched or expressed disinterest in

    private final MovieService movieService;

    @GetMapping("/movies/search")
    public ResponseEntity<List<Movie>> searchMovie(
        @RequestParam(required = false) Integer year,
        @RequestParam(required = false, value = "genre_name") String genreName,
        @RequestParam(required = false, value = "min_rating") Float minRating,
        @RequestParam(required = false, value = "max_rating") Float maxRating
    ) {
        log.debug("Request for searching with parameters");

        MovieSearchCriteriaDTO criteriaDTO =
            new MovieSearchCriteriaDTO()
                .setYear(year)
                .setName(genreName)
                .setMinRating(minRating)
                .setMaxRating(maxRating);

        List<Movie> movies = movieService.searchMovies(criteriaDTO);

        log.debug("Result for searching with parameters: {}", movies);
        return ResponseEntity.ok()
            .body(movies);

    }

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        log.debug("REST request to get all Movies");
        List<Movie> movies = movieService.getAll();
        log.debug("All movies: {}", movies);
        return ResponseEntity.ok()
            .body(movies);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable("id") Long id) throws BadRequestException {
        log.debug("REST request to get Movie : {}", id);
        Movie movie = movieService.getById(id)
            .orElseThrow(() -> new BadRequestException("Movie not found by this id"));
        log.debug("Found movie : {}", movie);
        return ResponseEntity.ok()
            .body(movie);
    }
}
