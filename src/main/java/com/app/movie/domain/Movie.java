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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@Entity
@ToString
@Table(name = "movies")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

    @Column(name = "release_year")
    private Instant releaseYear;

    @Column(name = "director")
    private String director;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_movie__genres",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "movie" }, allowSetters = true)
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movies")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "users", "movies" }, allowSetters = true)
    private Set<WatchList> watchLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "users", "movie" }, allowSetters = true)
    private Set<Review> reviews = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "movies")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "movie", "watchLists", "reviews" }, allowSetters = true)
    private Set<User> users = new HashSet<>();

    public Movie genres(Set<Genre> genres) {
        this.setGenres(genres);
        return this;
    }

    public Movie addGenres(Genre genre) {
        this.genres.add(genre);
        return this;
    }

    public Movie removeGenres(Genre genre) {
        this.genres.remove(genre);
        return this;
    }

    public void setWatchLists(Set<WatchList> watchLists) {
        if (this.watchLists != null) {
            this.watchLists.forEach(i -> i.setMovies(null));
        }
        if (watchLists != null) {
            watchLists.forEach(i -> i.setMovies(this));
        }
        this.watchLists = watchLists;
    }

    public Movie watchLists(Set<WatchList> watchLists) {
        this.setWatchLists(watchLists);
        return this;
    }

    public Movie addWatchList(WatchList watchList) {
        this.watchLists.add(watchList);
        watchList.setMovies(this);
        return this;
    }

    public Movie removeWatchList(WatchList watchList) {
        this.watchLists.remove(watchList);
        watchList.setMovies(null);
        return this;
    }

    public void setReviews(Set<Review> reviews) {
        if (this.reviews != null) {
            this.reviews.forEach(i -> i.setMovie(null));
        }
        if (reviews != null) {
            reviews.forEach(i -> i.setMovie(this));
        }
        this.reviews = reviews;
    }

    public Movie reviews(Set<Review> reviews) {
        this.setReviews(reviews);
        return this;
    }

    public Movie addReviews(Review review) {
        this.reviews.add(review);
        review.setMovie(this);
        return this;
    }

    public Movie removeReviews(Review review) {
        this.reviews.remove(review);
        review.setMovie(null);
        return this;
    }

    public void setUsers(Set<User> users) {
        if (this.users != null) {
            this.users.forEach(i -> i.removeMovies(this));
        }
        if (users != null) {
            users.forEach(i -> i.addMovies(this));
        }
        this.users = users;
    }

    public Movie users(Set<User> users) {
        this.setUsers(users);
        return this;
    }

    public Movie addUsers(User user) {
        this.users.add(user);
        user.getMovies().add(this);
        return this;
    }

    public Movie removeUsers(User user) {
        this.users.remove(user);
        user.getMovies().remove(this);
        return this;
    }
}
