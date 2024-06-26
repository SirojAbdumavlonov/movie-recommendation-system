package com.app.movie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Data
@Entity
@Accessors(chain = true)
@Table(name = "watch_list")
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = { "movie", "watchLists", "reviews" }, allowSetters = true)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "watch_list_movie",
        joinColumns = @JoinColumn(name = "watch_list_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    @JsonIgnoreProperties(value = { "genres", "watchLists", "reviews", "user" }, allowSetters = true)
    private List<Movie> movies = new ArrayList<>();

    public WatchList addMovie(Movie movie) {
        this.movies.add(movie);
        return this;
    }

    public WatchList removeMovie(Movie movie) {
        this.movies.remove(movie);
        return this;
    }

    @Override
    public String toString() {
        return "WatchList{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", user=" + user +
               ", movies=" + movies +
               '}';
    }
}
