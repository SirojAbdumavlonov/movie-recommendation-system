package com.app.movie.repository;

import com.app.movie.domain.Movie;
import com.app.movie.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT DISTINCT r.movie FROM Rating r " +
            "WHERE r.user.id = ?1 " +
            "ORDER BY r.createdAt DESC"
    )
    List<Movie> findRecentlyRatedMovies(Integer userId);
}
