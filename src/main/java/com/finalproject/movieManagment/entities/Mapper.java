package com.finalproject.movieManagment.entities;

import com.finalproject.movieManagment.dtos.ActorDTO;
import com.finalproject.movieManagment.dtos.MovieDTO;
import com.finalproject.movieManagment.dtos.RatingDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {

    public List<MovieDTO> convertMovie(List<Movie> movies) {
        List<MovieDTO> movieDTO = new ArrayList<>();

        for (Movie movie : movies) {
            movieDTO.add(new MovieDTO(movie));
        }

        return movieDTO;
    }

    public List<ActorDTO> convertActors(List<Actor> actors) {
        List<ActorDTO> actorDTO = new ArrayList<>();

        for (Actor actor : actors) {
            actorDTO.add(new ActorDTO(actor));
        }

        return actorDTO;
    }

    public List<RatingDTO> convertRating(List<Rating> ratings) {
        List<RatingDTO> ratingDTO = new ArrayList<>();

        for (Rating rating : ratings) {
            ratingDTO.add(new RatingDTO(rating));
        }

        return ratingDTO;
    }

}