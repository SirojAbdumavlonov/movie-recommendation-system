package com.app.movie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@ToString
@Data
@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class User implements Serializable, UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_users__movies",
        joinColumns = @JoinColumn(name = "users_id"),
        inverseJoinColumns = @JoinColumn(name = "movies_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "genres", "watchLists", "reviews", "users" }, allowSetters = true)
    private Set<Movie> movies = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "users", "movies" }, allowSetters = true)
    private Set<WatchList> watchLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "users", "movies" }, allowSetters = true)
    private Set<Review> reviews = new HashSet<>();

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public User email(String email) {
        this.setEmail(email);
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public User password(String password) {
        this.setPassword(password);
        return this;
    }

    public User movies(Set<Movie> movies) {
        this.setMovies(movies);
        return this;
    }

    public User addMovies(Movie movie) {
        this.movies.add(movie);
        return this;
    }

    public User removeMovies(Movie movie) {
        this.movies.remove(movie);
        return this;
    }

    public void setWatchLists(Set<WatchList> watchLists) {
        if (this.watchLists != null) {
            this.watchLists.forEach(i -> i.setUser(null));
        }
        if (watchLists != null) {
            watchLists.forEach(i -> i.setUser(this));
        }
        this.watchLists = watchLists;
    }

    public User watchLists(Set<WatchList> watchLists) {
        this.setWatchLists(watchLists);
        return this;
    }

    public User addWatchList(WatchList watchList) {
        this.watchLists.add(watchList);
        watchList.setUser(this);
        return this;
    }

    public User removeWatchList(WatchList watchList) {
        this.watchLists.remove(watchList);
        watchList.setUser(null);
        return this;
    }

    public void setReviews(Set<Review> reviews) {
        if (this.reviews != null) {
            this.reviews.forEach(i -> i.setUsers(null));
        }
        if (reviews != null) {
            reviews.forEach(i -> i.setUsers(this));
        }
        this.reviews = reviews;
    }

    public User reviews(Set<Review> reviews) {
        this.setReviews(reviews);
        return this;
    }

    public User addReviews(Review review) {
        this.reviews.add(review);
        review.setUsers(this);
        return this;
    }

    public User removeReviews(Review review) {
        this.reviews.remove(review);
        review.setUsers(null);
        return this;
    }

}
