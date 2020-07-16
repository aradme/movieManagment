package com.finalproject.movieManagment.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.finalproject.movieManagment.entities.Movie;
import com.finalproject.movieManagment.entities.Rating;
import lombok.Value;

import java.util.List;

@Value
@JsonPropertyOrder({"Rate","Votes"})

public class RatingDTO {
    @JsonIgnore
    private Rating rating;

    public double getRate(){
        return this.rating.getRate();
    }

    public Long getVotes(){
        return this.rating.getVotes();
    }

}
