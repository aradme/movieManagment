package com.finalproject.movieManagment.entities;

import com.finalproject.movieManagment.controllers.ActorController;
import com.finalproject.movieManagment.controllers.MovieController;
import com.finalproject.movieManagment.dtos.ActorDTO;
import com.finalproject.movieManagment.dtos.MovieDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ActorEntityAdapter implements RepresentationModelAssembler<ActorDTO, EntityModel<ActorDTO>> {

    @Override
    public EntityModel<ActorDTO> toModel(ActorDTO actorDTO) {

        return new EntityModel<>(actorDTO,
                linkTo(methodOn(ActorController.class).getActorById(actorDTO.getActor().getId()))
                        .withSelfRel(),
                linkTo(methodOn(MovieController.class).getAllMovies()).withRel("Movies"));
    }

}
