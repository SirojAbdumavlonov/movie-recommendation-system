package com.app.movie.controller;

import com.app.movie.domain.Review;
import com.app.movie.repository.ReviewRepository;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
@Transactional
@RequiredArgsConstructor
public class ReviewResource {

    private final ReviewRepository reviewRepository;

    @PostMapping("")
    public ResponseEntity<Review> createReviews(@RequestBody Review review) throws URISyntaxException, BadRequestException {
        log.debug("REST request to save Review : {}", review);
        if (review.getId() != null) {
            throw new BadRequestException("A new review cannot already have an ID");
        }
        review = reviewRepository.save(review);
        return ResponseEntity.created(new URI("/api/reviews/" + review.getId()))
            .body(review);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReviews(@PathVariable(value = "id", required = false) final Long id, @RequestBody Review review) throws BadRequestException {
        log.debug("REST request to update Review : {}, {}", id, review);
        if (review.getId() == null) {
            throw new BadRequestException("Invalid review id");
        }
        if (!Objects.equals(id, review.getId())) {
            throw new BadRequestException("Invalid review ID");
        }
        if (!reviewRepository.existsById(id)) {
            throw new BadRequestException("Entity not found");
        }

        review = reviewRepository.save(review);
        return ResponseEntity.ok()
            .body(review);
    }

    @GetMapping("")
    public List<Review> getAllReviews() {
        log.debug("REST request to get all Review");
        return reviewRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviews(@PathVariable("id") Long id) {
        log.debug("REST request to get Review : {}", id);
        Optional<Review> reviews = reviewRepository.findById(id);
        return ResponseEntity.ok(reviews.orElseThrow());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReviews(@PathVariable("id") Long id) {
        log.debug("REST request to delete Review : {}", id);
        reviewRepository.deleteById(id);
        return ResponseEntity.noContent()
            .build();
    }
}
