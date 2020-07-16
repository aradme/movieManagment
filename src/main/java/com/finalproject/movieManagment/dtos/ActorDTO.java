package com.finalproject.movieManagment.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.finalproject.movieManagment.entities.Actor;
import com.finalproject.movieManagment.entities.Movie;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
@JsonPropertyOrder({"Actor Name", "Birth Date","Death Date","Known For Movie"})
public class ActorDTO {

    @JsonIgnore
    private Actor actor;

    public String getActorName(){
        return this.actor.getActorName();
    }

    public LocalDate getActorBirthYear(){
        return this.actor.getBirthYear();
    }

    public LocalDate getActorDeathYear(){
        return this.actor.getDeathYear();
    }

    public String getActorKnownForMovies(){
        return this.actor.getKnownFor();
    }


}
