package com.app.movie.controller;

import com.app.movie.domain.Genres;
import com.app.movie.repository.GenresRepository;
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
 * REST controller for managing {@link package com.app.movie.controller.Genres}.
 */
@RestController
@RequestMapping("/api/genres")
@Transactional
public class GenresResource {

    private final Logger log = LoggerFactory.getLogger(GenresResource.class);

    private static final String ENTITY_NAME = "genres";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GenresRepository genresRepository;

    public GenresResource(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    /**
     * {@code POST  /genres} : Create a new genres.
     *
     * @param genres the genres to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new genres, or with status {@code 400 (Bad Request)} if the genres has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Genres> createGenres(@RequestBody Genres genres) throws URISyntaxException {
        log.debug("REST request to save Genres : {}", genres);
        if (genres.getId() != null) {
            throw new BadRequestAlertException("A new genres cannot already have an ID", ENTITY_NAME, "idexists");
        }
        genres = genresRepository.save(genres);
        return ResponseEntity.created(new URI("/api/genres/" + genres.getId()))
            .body(genres);
    }

    /**
     * {@code PUT  /genres/:id} : Updates an existing genres.
     *
     * @param id the id of the genres to save.
     * @param genres the genres to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated genres,
     * or with status {@code 400 (Bad Request)} if the genres is not valid,
     * or with status {@code 500 (Internal Server Error)} if the genres couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Genres> updateGenres(@PathVariable(value = "id", required = false) final Integer id, @RequestBody Genres genres)
        throws URISyntaxException {
        log.debug("REST request to update Genres : {}, {}", id, genres);
        if (genres.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, genres.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!genresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        genres = genresRepository.save(genres);
        return ResponseEntity.ok()
            .body(genres);
    }

    /**
     * {@code PATCH  /genres/:id} : Partial updates given fields of an existing genres, field will ignore if it is null
     *
     * @param id the id of the genres to save.
     * @param genres the genres to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated genres,
     * or with status {@code 400 (Bad Request)} if the genres is not valid,
     * or with status {@code 404 (Not Found)} if the genres is not found,
     * or with status {@code 500 (Internal Server Error)} if the genres couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Genres> partialUpdateGenres(
        @PathVariable(value = "id", required = false) final Integer id,
        @RequestBody Genres genres
    ) throws URISyntaxException {
        log.debug("REST request to partial update Genres partially : {}, {}", id, genres);
        if (genres.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, genres.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!genresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Genres> result = genresRepository
            .findById(genres.getId())
            .map(existingGenres -> {
                if (genres.getGenreName() != null) {
                    existingGenres.setGenreName(genres.getGenreName());
                }

                return existingGenres;
            })
            .map(genresRepository::save);

        return ResponseEntity.ok(result.orElse(genres));
    }

    /**
     * {@code GET  /genres} : get all the genres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of genres in body.
     */
    @GetMapping("")
    public List<Genres> getAllGenres() {
        log.debug("REST request to get all Genres");
        return genresRepository.findAll();
    }

    /**
     * {@code GET  /genres/:id} : get the "id" genres.
     *
     * @param id the id of the genres to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the genres, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Genres> getGenres(@PathVariable("id") Integer id) {
        log.debug("REST request to get Genres : {}", id);
        Optional<Genres> genres = genresRepository.findById(id);
        return ResponseEntity.ok(genres.orElseThrow());
    }

    /**
     * {@code DELETE  /genres/:id} : delete the "id" genres.
     *
     * @param id the id of the genres to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenres(@PathVariable("id") Integer id) {
        log.debug("REST request to delete Genres : {}", id);
        genresRepository.deleteById(id);
        return ResponseEntity.noContent()
            .build();
    }
}
