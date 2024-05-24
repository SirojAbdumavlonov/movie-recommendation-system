package com.app.movie.controller;

import com.app.movie.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/films")
public class FilmResource {
    private final FilmService filmService;

    @GetMapping("")
    public ResponseEntity<?> getFilms(@RequestParam int year) throws IOException, InterruptedException {

        Object films = filmService.getMoviesByYear(year);

        return ResponseEntity.ok(films);
    }
}
