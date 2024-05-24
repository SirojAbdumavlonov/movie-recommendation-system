package com.app.movie.service;

import com.app.movie.domain.Movie;
import com.app.movie.domain.Rating;
import com.app.movie.domain.User;
import com.app.movie.repository.MovieRepository;
import com.app.movie.repository.RatingRepository;
import com.app.movie.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;


    public RatingService(RatingRepository ratingRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public Rating rateMovie(String username, Float rate, Long movieId) throws BadRequestException {

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new BadRequestException("There is no user with this username!"));

        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new BadRequestException("There is no movie with this id!"));

        Rating rating = new Rating();

        rating.setUser(user);
        rating.setMovie(movie);
        rating.setRating(rate);
        rating.setCreatedAt(LocalDate.now());

        ratingRepository.save(rating);

        return rating;
    }

    public List<Movie> getRatedMoviesOfUser(String username) throws BadRequestException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("There is no user with this username!"));

        List<Movie> recentlyRatedMovies =
                ratingRepository.findRecentlyRatedMovies(user.getId());

        log.debug("Recently rated movies: {}", recentlyRatedMovies);

        return recentlyRatedMovies;
    }

    public void deleteRating(Long ratingId) throws BadRequestException {

        Rating rating =
                ratingRepository.findById(ratingId)
                        .orElseThrow(
                                () -> new BadRequestException("There is no rating with this id!")
                        );
        log.debug("Rating with id - {} deleted successfully", rating.getId());

        ratingRepository.delete(rating);

    }

    public Rating updateRating(Long id, Float rate) throws BadRequestException {
        Rating rating =
                ratingRepository.findById(id)
                        .orElseThrow(
                                () -> new BadRequestException("There is no rating with this id!")
                        );
        rating.setRating(rate);
        Rating updated = ratingRepository.save(rating);
        log.debug("Rating with id - {} updated successfully", updated.getId());

        return updated;
    }
}
