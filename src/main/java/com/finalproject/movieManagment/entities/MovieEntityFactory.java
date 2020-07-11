package com.finalproject.movieManagment.entities;

import com.finalproject.movieManagment.controllers.MovieController;
import org.springframework.stereotype.Component;

@Component
public class MovieEntityFactory extends SimpleIdentifiableRepresentationModelAssembler<Movie> {


    public MovieEntityFactory() {
        super(MovieController.class);
    }
}
