package com.app.movie.repository;

import com.app.movie.domain.Movie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, MovieRepositoryCustom {
    @Query("""
    SELECT DISTINCT m.title, m.director, m.release_year
    FROM Movies m
        INNER JOIN Ratings r ON m.movie_id = r.movie_id
        INNER JOIN Ratings r2 ON r.movie_id = r2.movie_id
    AND r.user_id <> r2.user_id
        WHERE r2.user_id = <user_id> AND r2.rating >= 4
        ORDER BY r.rating DESC
    """)
    List<Movie> findSimilar();
}

