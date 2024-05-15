package com.app.movie.repository;
import java.util.List;
import java.util.Optional;

import com.app.movie.domain.Movies;
import org.springframework.data.domain.Page;

public interface MoviesRepositoryWithBagRelationships {
    Optional<Movies> fetchBagRelationships(Optional<Movies> movies);

    List<Movies> fetchBagRelationships(List<Movies> movies);

    Page<Movies> fetchBagRelationships(Page<Movies> movies);
}
