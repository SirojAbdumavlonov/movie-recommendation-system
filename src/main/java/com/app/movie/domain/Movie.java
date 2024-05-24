package com.app.movie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@Entity
@ToString
@Table(name = "movies")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Accessors(chain = true)
public class Movie implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_date")
    private Instant releaseDate;

    @Column(name = "director")
    private String director;

    @Column(name = "trailer_url")
    private String trailerUrl;

    @Column(name = "trailer_image_url")
    private String trailerImageUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_movie__genres",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "movie" }, allowSetters = true)
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "watch_list_movie",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "watch_list_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "movies", "reviews" }, allowSetters = true)
    private Set<WatchList> watchLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "movie" }, allowSetters = true)
    private Set<Review> reviews = new HashSet<>();
}
