package com.app.movie.service;

import com.app.movie.domain.Movie;
import com.app.movie.domain.Review;
import com.app.movie.domain.User;
import com.app.movie.dto.ReviewDTO;
import com.app.movie.repository.MovieRepository;
import com.app.movie.repository.ReviewRepository;
import com.app.movie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public Review addReview(ReviewDTO reviewDTO, String username) throws Exception {
        Review review = new Review();

        Movie movie = movieRepository.findById(reviewDTO.movieId())
            .orElseThrow(() -> new BadRequestException("There is no movie with this id!"));

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new BadRequestException("There is no user with this username!"));

        review.setReviewText(reviewDTO.reviewText());
        review.setUser(user);
        review.setMovie(movie);

        reviewRepository.save(review);

        return review;

    }

}
