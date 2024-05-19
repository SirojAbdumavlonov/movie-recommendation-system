package com.app.movie.repository;

import com.app.movie.domain.Movie;
import com.app.movie.dto.MovieSearchCriteriaDTO;

import java.util.List;

public interface MovieRepositoryCustom {
    List<Movie> searchMovies(MovieSearchCriteriaDTO criteria);
}


