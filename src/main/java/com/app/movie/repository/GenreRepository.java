package com.app.movie.repository;
import com.app.movie.domain.Genre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {}
