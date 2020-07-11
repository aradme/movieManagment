/*
package com.finalproject.movieManagment.entities;
import com.finalproject.movieManagment.controllers.MovieController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EntityAdapter implements RepresentationModelAssembler<Movie, EntityModel<Movie>> {

    @Override
    public EntityModel<Movie> toModel(Movie movie) {

        return new EntityModel<>(movie,
                //linkTo(methodOn(MovieController.class).getSingleProduct(product.getId())).withSelfRel(),
                linkTo(methodOn(MovieController.class).getSingleMovie(movie.getPrimaryTitle())).withSelfRel());
    }
}*/
