package com.app.movie.controller;

import com.app.movie.domain.Movies;
import com.app.movie.repository.MoviesRepository;
import com.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link package com.app.movie.controller.Movies}.
 */
@RestController
@RequestMapping("/api/movies")
@Transactional
public class MoviesResource {

    private final Logger log = LoggerFactory.getLogger(MoviesResource.class);

    private static final String ENTITY_NAME = "movies";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MoviesRepository moviesRepository;

    public MoviesResource(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    /**
     * {@code POST  /movies} : Create a new movies.
     *
     * @param movies the movies to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movies, or with status {@code 400 (Bad Request)} if the movies has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Movies> createMovies(@RequestBody Movies movies) throws URISyntaxException {
        log.debug("REST request to save Movies : {}", movies);
        if (movies.getId() != null) {
            throw new BadRequestAlertException("A new movies cannot already have an ID", ENTITY_NAME, "idexists");
        }
        movies = moviesRepository.save(movies);
        return ResponseEntity.created(new URI("/api/movies/" + movies.getId()))
            .body(movies);
    }

    /**
     * {@code PUT  /movies/:id} : Updates an existing movies.
     *
     * @param id the id of the movies to save.
     * @param movies the movies to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movies,
     * or with status {@code 400 (Bad Request)} if the movies is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movies couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Movies> updateMovies(@PathVariable(value = "id", required = false) final Long id, @RequestBody Movies movies)
        throws URISyntaxException {
        log.debug("REST request to update Movies : {}, {}", id, movies);
        if (movies.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movies.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moviesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        movies = moviesRepository.save(movies);
        return ResponseEntity.ok()
            .body(movies);
    }

    /**
     * {@code PATCH  /movies/:id} : Partial updates given fields of an existing movies, field will ignore if it is null
     *
     * @param id the id of the movies to save.
     * @param movies the movies to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movies,
     * or with status {@code 400 (Bad Request)} if the movies is not valid,
     * or with status {@code 404 (Not Found)} if the movies is not found,
     * or with status {@code 500 (Internal Server Error)} if the movies couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Movies> partialUpdateMovies(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Movies movies
    ) throws URISyntaxException {
        log.debug("REST request to partial update Movies partially : {}, {}", id, movies);
        if (movies.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, movies.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moviesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Movies> result = moviesRepository
            .findById(movies.getId())
            .map(existingMovies -> {
                if (movies.getTitle() != null) {
                    existingMovies.setTitle(movies.getTitle());
                }
                if (movies.getDescription() != null) {
                    existingMovies.setDescription(movies.getDescription());
                }
                if (movies.getReleaseYear() != null) {
                    existingMovies.setReleaseYear(movies.getReleaseYear());
                }
                if (movies.getDirector() != null) {
                    existingMovies.setDirector(movies.getDirector());
                }

                return existingMovies;
            })
            .map(moviesRepository::save);

        return ResponseEntity.ok(result.orElse(movies));
    }

    /**
     * {@code GET  /movies} : get all the movies.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movies in body.
     */
    @GetMapping("")
    public List<Movies> getAllMovies(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Movies");
        if (eagerload) {
            return moviesRepository.findAllWithEagerRelationships();
        } else {
            return moviesRepository.findAll();
        }
    }

    /**
     * {@code GET  /movies/:id} : get the "id" movies.
     *
     * @param id the id of the movies to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movies, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Movies> getMovies(@PathVariable("id") Long id) {
        log.debug("REST request to get Movies : {}", id);
        Optional<Movies> movies = moviesRepository.findOneWithEagerRelationships(id);
        return ResponseEntity.ok(movies.orElse(new Movies()));
    }

    /**
     * {@code DELETE  /movies/:id} : delete the "id" movies.
     *
     * @param id the id of the movies to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovies(@PathVariable("id") Long id) {
        log.debug("REST request to delete Movies : {}", id);
        moviesRepository.deleteById(id);
        return ResponseEntity.noContent()
            .build();
    }
}
