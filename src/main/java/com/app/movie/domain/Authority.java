package com.app.movie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@ToString
@Table(name = "jhi_authority")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "new", "id" })
public class Authority implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Transient
    private boolean isPersisted;
}
