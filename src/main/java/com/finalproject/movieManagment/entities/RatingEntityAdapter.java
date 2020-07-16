package com.finalproject.movieManagment.entities;

import com.finalproject.movieManagment.controllers.ActorController;
import com.finalproject.movieManagment.controllers.MovieController;
import com.finalproject.movieManagment.controllers.RatingController;
import com.finalproject.movieManagment.dtos.ActorDTO;
import com.finalproject.movieManagment.dtos.RatingDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RatingEntityAdapter implements RepresentationModelAssembler<RatingDTO, EntityModel<RatingDTO>> {

    @Override
    public EntityModel<RatingDTO> toModel(RatingDTO ratingDTO) {

        return new EntityModel<>(ratingDTO,
                linkTo(methodOn(RatingController.class).getRatingByMovie(ratingDTO.getRating().getId()))
                        .withSelfRel(),
                linkTo(methodOn(MovieController.class).getAllMovies()).withRel("Ratings"));
    }

}
