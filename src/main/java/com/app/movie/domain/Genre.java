package com.app.movie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@ToString
@Data
@Table(name = "genres")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Genre implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "genre_name")
    private String genreName;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "genres")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "genres", "watchLists", "reviews", "users" }, allowSetters = true)
    private Set<Movie> movies = new HashSet<>();

    public Genre movies(Set<Movie> movies) {
        this.setMovies(movies);
        return this;
    }

    public Genre addMovies(Movie movie) {
        this.movies.add(movie);
        movie.getGenres().add(this);
        return this;
    }

    public Genre removeMovies(Movie movie) {
        this.movies.remove(movie);
        movie.getGenres().remove(this);
        return this;
    }
}
