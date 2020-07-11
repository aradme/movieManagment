package com.finalproject.movieManagment.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.finalproject.movieManagment.entities.Movie;
import lombok.Value;

@Value
@JsonPropertyOrder({"movie name", "release date", "duration","genres"})
public class MovieDTO {

    @JsonIgnore
    private Movie movie;


    public String getPrimaryTitle() {
        return this.movie.getPrimaryTitle();
    }

    public double getStartYear() {
        return this.movie.getStartYear();
    }

    public double getRuntimeMinutes() {
        return this.movie.getRuntimeMinutes();
    }

    public String getGenres() {
        return this.movie.getGenres();
    }

}
