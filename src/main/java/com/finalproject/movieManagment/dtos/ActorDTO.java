package com.finalproject.movieManagment.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.finalproject.movieManagment.entities.Actor;
import lombok.Value;

@Value
@JsonPropertyOrder({"actor name", "birth year", "death year","known for movies"})
public class ActorDTO {

    @JsonIgnore
    private Actor actor;

    public String getActorName(){
        return this.actor.getPrimaryName();
    }

    public double getActorBirthYear(){
        return this.actor.getBirthYear();
    }

    public double getActorDeathYear(){
        return this.actor.getDeathYear();
    }

    public String getActorKnownForMovies(){
        return this.actor.getKnownForTitles();
    }


}
