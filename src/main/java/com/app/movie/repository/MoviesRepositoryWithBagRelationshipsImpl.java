package com.app.movie.repository;
import com.app.movie.domain.Movies;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class MoviesRepositoryWithBagRelationshipsImpl implements MoviesRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String MOVIES_PARAMETER = "movies";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Movies> fetchBagRelationships(Optional<Movies> movies) {
        return movies.map(this::fetchGenres);
    }

    @Override
    public Page<Movies> fetchBagRelationships(Page<Movies> movies) {
        return new PageImpl<>(fetchBagRelationships(movies.getContent()), movies.getPageable(), movies.getTotalElements());
    }

    @Override
    public List<Movies> fetchBagRelationships(List<Movies> movies) {
        return Optional.of(movies).map(this::fetchGenres).orElse(Collections.emptyList());
    }

    Movies fetchGenres(Movies result) {
        return entityManager
            .createQuery("select movies from Movies movies left join fetch movies.genres where movies.id = :id", Movies.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Movies> fetchGenres(List<Movies> movies) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, movies.size()).forEach(index -> order.put(movies.get(index).getId(), index));
        List<Movies> result = entityManager
            .createQuery("select movies from Movies movies left join fetch movies.genres where movies in :movies", Movies.class)
            .setParameter(MOVIES_PARAMETER, movies)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
