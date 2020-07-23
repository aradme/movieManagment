package com.finalproject.movieManagment.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.finalproject.movieManagment.entities.Movie;
import com.finalproject.movieManagment.entities.Rating;
import lombok.Value;

import java.time.LocalDate;

@Value
@JsonPropertyOrder({"movie name", "release date", "duration","genre"})
public class MovieDTO {

    @JsonIgnore
    private Movie movie;


    public String getMovieName() {
        return this.movie.getMovieTitle();
    }

    public LocalDate getReleaseDate() {
        return this.movie.getReleaseDate();
    }

    public double getDuration() {
        return this.movie.getDuration();
    }

    public String getGenres() {
        return this.movie.getGenre();
    }

}
