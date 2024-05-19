package com.app.movie.controller;

import com.app.movie.domain.Genre;
import com.app.movie.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/api/genres")
@Transactional
@RequiredArgsConstructor
public class GenreResource {
    private final GenreRepository genreRepository;

    @PostMapping("")
    public ResponseEntity<Genre> createGenres(@RequestBody Genre genre) throws Exception {
        log.debug("REST request to save Genre : {}", genre);
        if (genre.getId() != null) {
            throw new BadRequestException("A new genre cannot already have an ID");
        }
        genre = genreRepository.save(genre);
        return ResponseEntity.created(new URI("/api/genres/" + genre.getId()))
            .body(genre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenres(@PathVariable(value = "id", required = false) final Integer id, @RequestBody Genre genre)
        throws Exception {
        log.debug("REST request to update Genre : {}, {}", id, genre);
        if (genre.getId() == null) {
            throw new BadRequestException("Invalid genre id");
        }
        if (!Objects.equals(id, genre.getId())) {
            throw new BadRequestException("Invalid genre ID");
        }

        if (!genreRepository.existsById(id)) {
            throw new BadRequestException("Entity not found");
        }

        genre = genreRepository.save(genre);
        return ResponseEntity.ok()
            .body(genre);
    }


    @GetMapping("")
    public List<Genre> getAllGenres() {
        log.debug("REST request to get all Genre");
        return genreRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenres(@PathVariable("id") Integer id) {
        log.debug("REST request to get Genre : {}", id);
        Optional<Genre> genres = genreRepository.findById(id);
        return ResponseEntity.ok(genres.orElseThrow());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenres(@PathVariable("id") Integer id) {
        log.debug("REST request to delete Genre : {}", id);
        genreRepository.deleteById(id);
        return ResponseEntity.noContent()
            .build();
    }
}
