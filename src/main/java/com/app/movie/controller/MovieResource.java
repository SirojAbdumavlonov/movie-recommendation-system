package com.app.movie.controller;

import com.app.movie.domain.Movie;
import com.app.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Transactional
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MovieResource {

    private final MovieService movieService;

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
