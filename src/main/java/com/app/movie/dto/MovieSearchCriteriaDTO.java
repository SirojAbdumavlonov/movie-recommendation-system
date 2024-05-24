package com.app.movie.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class MovieSearchCriteriaDTO {
    private String title;
    private Float minRating;
    private Float maxRating;
    private Integer year;
    private String director;
}
