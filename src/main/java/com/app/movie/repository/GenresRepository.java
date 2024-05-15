package com.app.movie.repository;
import com.app.movie.domain.Genres;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Genres entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenresRepository extends JpaRepository<Genres, Integer> {}
