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
@Table(name = "watch_list")
@ToString
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WatchList implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "movie", "watchLists", "reviews" }, allowSetters = true)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "watch_list_movie",
        joinColumns = @JoinColumn(name = "watch_list_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    @JsonIgnoreProperties(value = { "genres", "watchLists", "reviews", "user" }, allowSetters = true)
    private Set<Movie> movies = new HashSet<>();

    public WatchList addMovie(Movie movie) {
        this.movies.add(movie);
        movie.getWatchLists().add(this);
        return this;
    }
}
