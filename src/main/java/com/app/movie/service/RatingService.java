package com.app.movie.service;

import com.app.movie.domain.Movie;
import com.app.movie.domain.Rating;
import com.app.movie.domain.User;
import com.app.movie.repository.MovieRepository;
import com.app.movie.repository.RatingRepository;
import com.app.movie.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;


    public RatingService(RatingRepository ratingRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public Rating rateMovie(String username, Float rate, Long movieId){

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new BadRequestException("There is no user with this username!"));

        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new BadRequestException("There is no movie with this id!"));

        Rating rating = new Rating();

        rating.setUser(user);
        rating.setMovie(movie);
        rating.setRating(rate);

        ratingRepository.save(rating);

        return rating;
    }

}
