package com.app.movie.repository.impl;

import com.app.movie.domain.Movie;
import com.app.movie.dto.MovieSearchCriteriaDTO;
import com.app.movie.repository.MovieRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieRepositoryCustomImpl implements MovieRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Movie> searchMovies(MovieSearchCriteriaDTO criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> query = cb.createQuery(Movie.class);
        Root<Movie> movie = query.from(Movie.class);

        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getTitle() != null && !criteria.getTitle().isEmpty()) {
            predicates.add(cb.like(cb.lower(movie.get("name")), "%" + criteria.getTitle().toLowerCase() + "%"));
        }
        if (criteria.getYear() != null) {
            predicates.add(cb.equal(movie.get("year"), criteria.getYear()));
        }
        if (criteria.getDirector() != null){
            predicates.add(cb.equal(movie.get("director"), criteria.getDirector()));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}
