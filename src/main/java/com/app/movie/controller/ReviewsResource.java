package com.app.movie.controller;

import com.app.movie.domain.Reviews;
import com.app.movie.repository.ReviewsRepository;
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
 * REST controller for managing {@link package com.app.movie.controller.Reviews}.
 */
@RestController
@RequestMapping("/api/reviews")
@Transactional
public class ReviewsResource {

    private final Logger log = LoggerFactory.getLogger(ReviewsResource.class);

    private static final String ENTITY_NAME = "reviews";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReviewsRepository reviewsRepository;

    public ReviewsResource(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }

    /**
     * {@code POST  /reviews} : Create a new reviews.
     *
     * @param reviews the reviews to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reviews, or with status {@code 400 (Bad Request)} if the reviews has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Reviews> createReviews(@RequestBody Reviews reviews) throws URISyntaxException {
        log.debug("REST request to save Reviews : {}", reviews);
        if (reviews.getId() != null) {
            throw new BadRequestAlertException("A new reviews cannot already have an ID", ENTITY_NAME, "idexists");
        }
        reviews = reviewsRepository.save(reviews);
        return ResponseEntity.created(new URI("/api/reviews/" + reviews.getId()))
            .body(reviews);
    }

    /**
     * {@code PUT  /reviews/:id} : Updates an existing reviews.
     *
     * @param id the id of the reviews to save.
     * @param reviews the reviews to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reviews,
     * or with status {@code 400 (Bad Request)} if the reviews is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reviews couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Reviews> updateReviews(@PathVariable(value = "id", required = false) final Long id, @RequestBody Reviews reviews)
        throws URISyntaxException {
        log.debug("REST request to update Reviews : {}, {}", id, reviews);
        if (reviews.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reviews.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reviewsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        reviews = reviewsRepository.save(reviews);
        return ResponseEntity.ok()
            .body(reviews);
    }

    /**
     * {@code PATCH  /reviews/:id} : Partial updates given fields of an existing reviews, field will ignore if it is null
     *
     * @param id the id of the reviews to save.
     * @param reviews the reviews to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reviews,
     * or with status {@code 400 (Bad Request)} if the reviews is not valid,
     * or with status {@code 404 (Not Found)} if the reviews is not found,
     * or with status {@code 500 (Internal Server Error)} if the reviews couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Reviews> partialUpdateReviews(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Reviews reviews
    ) throws URISyntaxException {
        log.debug("REST request to partial update Reviews partially : {}, {}", id, reviews);
        if (reviews.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reviews.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reviewsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Reviews> result = reviewsRepository
            .findById(reviews.getId())
            .map(existingReviews -> {
                if (reviews.getReviewText() != null) {
                    existingReviews.setReviewText(reviews.getReviewText());
                }

                return existingReviews;
            })
            .map(reviewsRepository::save);

        return ResponseEntity.ok(result.orElseThrow());
    }

    /**
     * {@code GET  /reviews} : get all the reviews.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reviews in body.
     */
    @GetMapping("")
    public List<Reviews> getAllReviews() {
        log.debug("REST request to get all Reviews");
        return reviewsRepository.findAll();
    }

    /**
     * {@code GET  /reviews/:id} : get the "id" reviews.
     *
     * @param id the id of the reviews to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reviews, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reviews> getReviews(@PathVariable("id") Long id) {
        log.debug("REST request to get Reviews : {}", id);
        Optional<Reviews> reviews = reviewsRepository.findById(id);
        return ResponseEntity.ok(reviews.orElseThrow());
    }

    /**
     * {@code DELETE  /reviews/:id} : delete the "id" reviews.
     *
     * @param id the id of the reviews to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviews(@PathVariable("id") Long id) {
        log.debug("REST request to delete Reviews : {}", id);
        reviewsRepository.deleteById(id);
        return ResponseEntity.noContent()
            .build();
    }
}
