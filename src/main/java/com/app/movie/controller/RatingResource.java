package com.app.movie.controller;

import com.app.movie.security.CurrentUserId;
import com.app.movie.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/rating")
public class RatingResource {

    private final RatingService ratingService;

    public RatingResource(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("")
    public ResponseEntity<?> rateMovie(@CurrentUserId UserDetails user,
                                    @RequestBody Float rating,
                                    @RequestParam(name = "movie_id") Long movieId){

        log.debug("Rating movie with id - {} by user with username - {}", movieId, user.getUsername());

        Rating rating = ratingService.rateMovie(user.getUsername(), rating, movieId);


        return ResponseEntity.created(new URI("/api/rating/" + rating.getId()))
            .body("Rated successfully!");
    }
}
