package com.app.movie.service;

import com.app.movie.domain.Movie;
import com.app.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

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

    public Optional<Movie> getById(Long id) {
        log.debug("Getting movie by id: {}", id);
        Optional<Movie> movie = movieRepository.findById(id);
        log.debug("Found movie: {}", movie);
        return movie;
    }
}
