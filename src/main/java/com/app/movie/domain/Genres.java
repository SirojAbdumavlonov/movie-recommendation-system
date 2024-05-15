package com.app.movie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Genres.
 */
@Entity
@Table(name = "genres")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Genres implements Serializable {

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
    private Set<Movies> movies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public Genres id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenreName() {
        return this.genreName;
    }

    public Genres genreName(String genreName) {
        this.setGenreName(genreName);
        return this;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Set<Movies> getMovies() {
        return this.movies;
    }

    public void setMovies(Set<Movies> movies) {
        if (this.movies != null) {
            this.movies.forEach(i -> i.removeGenres(this));
        }
        if (movies != null) {
            movies.forEach(i -> i.addGenres(this));
        }
        this.movies = movies;
    }

    public Genres movies(Set<Movies> movies) {
        this.setMovies(movies);
        return this;
    }

    public Genres addMovies(Movies movies) {
        this.movies.add(movies);
        movies.getGenres().add(this);
        return this;
    }

    public Genres removeMovies(Movies movies) {
        this.movies.remove(movies);
        movies.getGenres().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Genres)) {
            return false;
        }
        return getId() != null && getId().equals(((Genres) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Genres{" +
            "id=" + getId() +
            ", genreName='" + getGenreName() + "'" +
            "}";
    }
}
