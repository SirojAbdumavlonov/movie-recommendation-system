package com.app.movie.repository;

import com.app.movie.domain.Movie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, MovieRepositoryCustom {
    @Query("""
    SELECT DISTINCT m.title, m.director, m.releaseDate
    FROM Movie m
    """)
    List<Movie> findSimilar();
}

