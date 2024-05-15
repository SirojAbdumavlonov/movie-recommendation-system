package com.app.movie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Movies.
 */
@Entity
@Table(name = "movies")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Movies implements Serializable {

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
        name = "rel_movies__genres",
        joinColumns = @JoinColumn(name = "movies_id"),
        inverseJoinColumns = @JoinColumn(name = "genres_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "movies" }, allowSetters = true)
    private Set<Genres> genres = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movies")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "users", "movies" }, allowSetters = true)
    private Set<WatchList> watchLists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movies")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "users", "movies" }, allowSetters = true)
    private Set<Reviews> reviews = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "movies")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "movies", "watchLists", "reviews" }, allowSetters = true)
    private Set<Users> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Movies id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Movies title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Movies description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getReleaseYear() {
        return this.releaseYear;
    }

    public Movies releaseYear(Instant releaseYear) {
        this.setReleaseYear(releaseYear);
        return this;
    }

    public void setReleaseYear(Instant releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDirector() {
        return this.director;
    }

    public Movies director(String director) {
        this.setDirector(director);
        return this;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Set<Genres> getGenres() {
        return this.genres;
    }

    public void setGenres(Set<Genres> genres) {
        this.genres = genres;
    }

    public Movies genres(Set<Genres> genres) {
        this.setGenres(genres);
        return this;
    }

    public Movies addGenres(Genres genres) {
        this.genres.add(genres);
        return this;
    }

    public Movies removeGenres(Genres genres) {
        this.genres.remove(genres);
        return this;
    }

    public Set<WatchList> getWatchLists() {
        return this.watchLists;
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

    public Movies watchLists(Set<WatchList> watchLists) {
        this.setWatchLists(watchLists);
        return this;
    }

    public Movies addWatchList(WatchList watchList) {
        this.watchLists.add(watchList);
        watchList.setMovies(this);
        return this;
    }

    public Movies removeWatchList(WatchList watchList) {
        this.watchLists.remove(watchList);
        watchList.setMovies(null);
        return this;
    }

    public Set<Reviews> getReviews() {
        return this.reviews;
    }

    public void setReviews(Set<Reviews> reviews) {
        if (this.reviews != null) {
            this.reviews.forEach(i -> i.setMovies(null));
        }
        if (reviews != null) {
            reviews.forEach(i -> i.setMovies(this));
        }
        this.reviews = reviews;
    }

    public Movies reviews(Set<Reviews> reviews) {
        this.setReviews(reviews);
        return this;
    }

    public Movies addReviews(Reviews reviews) {
        this.reviews.add(reviews);
        reviews.setMovies(this);
        return this;
    }

    public Movies removeReviews(Reviews reviews) {
        this.reviews.remove(reviews);
        reviews.setMovies(null);
        return this;
    }

    public Set<Users> getUsers() {
        return this.users;
    }

    public void setUsers(Set<Users> users) {
        if (this.users != null) {
            this.users.forEach(i -> i.removeMovies(this));
        }
        if (users != null) {
            users.forEach(i -> i.addMovies(this));
        }
        this.users = users;
    }

    public Movies users(Set<Users> users) {
        this.setUsers(users);
        return this;
    }

    public Movies addUsers(Users users) {
        this.users.add(users);
        users.getMovies().add(this);
        return this;
    }

    public Movies removeUsers(Users users) {
        this.users.remove(users);
        users.getMovies().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movies)) {
            return false;
        }
        return getId() != null && getId().equals(((Movies) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Movies{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", releaseYear='" + getReleaseYear() + "'" +
            ", director='" + getDirector() + "'" +
            "}";
    }
}
