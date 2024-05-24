package com.app.movie.controller;

import com.app.movie.domain.Movie;
import com.app.movie.dto.MovieSearchCriteriaDTO;
import com.app.movie.service.MovieService;
import com.app.movie.utils.ResponseUtils;
import com.app.movie.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MovieResource {

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
                .setTitle(genreName)
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
        String userToken = SecurityUtils.getCurrentUserToken();
        System.out.println(userToken);
        List<Movie> movies = movieService.getAll();
        log.debug("All movies: {}", movies);
        return ResponseEntity.ok()
            .body(movies);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable("id") Long id) {
        log.debug("REST request to get Movie : {}", id);
        Optional<Movie> movie = movieService.getById(id);
        log.debug("Movie : {}", movie);
        return ResponseUtils.wrapOrNotFound(movie);
    }

    @GetMapping("/movies/{id}/recommendation")
    public ResponseEntity<List<Movie>> getSimilarMovies(@PathVariable Long id) {
        log.debug("REST request to get Similar Movies. MovieId : {}", id);
        List<Movie> similarMovies = movieService.getSimilarMovies(id);
        log.debug("Similar movies : {}", similarMovies);
        return ResponseEntity.ok()
            .body(similarMovies);
    }
}
