package com.app.movie.service;

import com.app.movie.domain.Movie;
import com.app.movie.domain.Review;
import com.app.movie.domain.User;
import com.app.movie.dto.ReviewDTO;
import com.app.movie.repository.MovieRepository;
import com.app.movie.repository.ReviewRepository;
import com.app.movie.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    public ReviewService(ReviewRepository reviewRepository, ReviewRepository reviewRepository1, MovieRepository movieRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository1;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public Review addReview(ReviewDTO reviewDTO, String username) throws BadRequestException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("There is no user with this username!"));

        Movie movie = movieRepository.findById(reviewDTO.movieId())
                .orElseThrow(() -> new BadRequestException("There is no movie with this id!"));

        Review review = new Review();
        review.setReviewText(reviewDTO.reviewText());
        review.setUser(user);
        review.setMovie(movie);

        reviewRepository.save(review);
        return review;

    }
}
