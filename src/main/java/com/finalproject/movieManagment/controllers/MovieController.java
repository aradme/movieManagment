package com.finalproject.movieManagment.controllers;

import com.finalproject.movieManagment.database.MovieDB;
import com.finalproject.movieManagment.dtos.MovieDTO;
import com.finalproject.movieManagment.entities.MovieEntityAdapter;
import com.finalproject.movieManagment.entities.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class MovieController {
    @Autowired
    MovieDB movieDB;
    @Autowired
    private Mapper mapper;
    @Autowired
    private MovieEntityAdapter movieEntityAdapter;


    @GetMapping("/movies")
    public ResponseEntity<CollectionModel<EntityModel<MovieDTO>>> getAllMovies() {
        List<MovieDTO> movies = mapper.convertMovie(movieDB.findTop1000By());
        List<EntityModel<MovieDTO>> moviesEntityModel = new ArrayList<>();
        for (MovieDTO movieDTO : movies) {
            EntityModel<MovieDTO> movieEntityModel = movieEntityAdapter.toModel(movieDTO);
            moviesEntityModel.add(movieEntityModel);
        }
        return ResponseEntity.ok(new CollectionModel<>(moviesEntityModel,
                linkTo(methodOn(MovieController.class).getAllMovies()).withSelfRel()));
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<EntityModel<MovieDTO>> getMovieById(@PathVariable String id) {
        MovieDTO movieDTO = new MovieDTO(
                movieDB.findById(id).orElseThrow(() -> new MovieNotFoundException(id)));
        return ResponseEntity.ok(movieEntityAdapter.toModel(movieDTO));
    }
    @GetMapping("/actors/{id}/movie")
    public ResponseEntity<CollectionModel<EntityModel<MovieDTO>>> getMovieByActor(@PathVariable String id) {
        List<MovieDTO> moviesDTO;
        List<EntityModel<MovieDTO>> moviesEntityModel = new ArrayList<>();
        for (MovieDTO movieDTO : moviesDTO) {
            EntityModel<MovieDTO> movieEntityModel = movieEntityAdapter.toModel(movieDTO);
            moviesEntityModel.add(movieEntityModel);
        }
        return ResponseEntity.ok(new CollectionModel<>(moviesEntityModel,
                linkTo(methodOn(MovieController.class).getAllMovies()).withSelfRel()));
    }

}























































/*MovieDB movieDB;

    MovieEntityFactory movieEntityFactory;

    EntityAdapter entityAdapter;

    @GetMapping("/movies")
    public ResponseEntity<CollectionModel<EntityModel<Movie>>> getAllMovies(){
        return ResponseEntity.ok(movieEntityFactory.toCollectionModel(movieDB.findTop1000By()));
    }

    *//*@GetMapping("/movies")
    CollectionModel<EntityModel<Movie>> getAllMovies() {
        List<EntityModel<Movie>> movies = new ArrayList<>();
        for (Movie movie : movieDB.findAll()) {
            EntityModel<Movie> movieEntityModel = entityAdapter.toModel(movie);
            movies.add(movieEntityModel);
        }
        return new CollectionModel<>(movies,
                linkTo(methodOn(MovieController.class).getAllMovies()).withSelfRel());
    }*//*

    @GetMapping("/movie/{id}")
   public EntityModel<Movie> getSingleMovie(@PathVariable String id) {

        Movie product = movieDB.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
        return entityAdapter.toModel(product);
    }*/