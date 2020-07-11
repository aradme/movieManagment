package com.finalproject.movieManagment.entities;

import com.finalproject.movieManagment.controllers.MovieController;
import com.finalproject.movieManagment.dtos.MovieDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MovieEntityAdapter implements RepresentationModelAssembler<MovieDTO, EntityModel<MovieDTO>> {

    @Override
    public EntityModel<MovieDTO> toModel(MovieDTO movieDTO) {

        return new EntityModel<>(movieDTO,
                linkTo(methodOn(MovieController.class).getMovieById(movieDTO.getPrimaryTitle()))
                        .withSelfRel(),
                linkTo(methodOn(MovieController.class).getAllMovies()).withRel("Movies"));
    }

}
