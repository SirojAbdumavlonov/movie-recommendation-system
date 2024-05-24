package com.app.movie.controller;

import com.app.movie.domain.Movie;
import com.app.movie.domain.Rating;
import com.app.movie.security.CurrentUserId;
import com.app.movie.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/rating")
public class RatingResource {

    private final RatingService ratingService;

    public RatingResource(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("")
    public ResponseEntity<?> rateMovie(
            @CurrentUserId UserDetails user,
            @RequestBody Float rate,
            @RequestParam(name = "movie_id") Long movieId
    ) throws BadRequestException, URISyntaxException {

        log.debug("Rating movie with id - {} by user with username - {}", movieId, user.getUsername());

        Rating rating = ratingService.rateMovie(user.getUsername(), rate, movieId);

        return ResponseEntity.created(new URI("/api/rating/" + rating.getId()))
            .body("Rated successfully!");
    }
    @GetMapping("/rated-movies")
    public ResponseEntity<List<Movie>> getRecentlyRatedMovies(
            @CurrentUserId UserDetails user
    ) throws BadRequestException {
        log.debug("Recently rated movies of user with username - {}", user.getUsername());

        List<Movie> recentlyRatedMovies =
                ratingService.getRatedMoviesOfUser(user.getUsername());

        return ResponseEntity.ok(recentlyRatedMovies);
    }
    @DeleteMapping("")
    public ResponseEntity<?> deleteRating(
            @CurrentUserId UserDetails user,
            @RequestParam(name = "rating_id") Long ratingId
    ) throws BadRequestException {

        log.debug("Delete a rating of user with username - {} and movie id - {}",
                user.getUsername(), ratingId);

        ratingService.deleteRating(ratingId);

        return ResponseEntity.ok(
                "Deleted a rating of user with username - "
                        + user.getUsername());
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRating(
            @PathVariable Long id,
            @RequestBody Float rate,
            @CurrentUserId UserDetails user
    ) throws BadRequestException {

        log.debug("Update a rating with id - {} of user with username - {} to new rating - {}",
                id, user.getUsername() , rate);

        Rating rating = ratingService.updateRating(id, rate);

        return ResponseEntity.ok()
                .body(rating);
    }
}
