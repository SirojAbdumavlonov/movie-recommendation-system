package com.app.movie.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@ToString
@Entity
@Table(name = "reviews")
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    private Float rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "movie", "watchLists", "reviews" }, allowSetters = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "genres", "watchLists", "reviews", "users" }, allowSetters = true)
    private Movie movie;

}
