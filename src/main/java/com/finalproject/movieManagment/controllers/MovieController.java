package com.finalproject.movieManagment.controllers;

import com.finalproject.movieManagment.database.ActorDB;
import com.finalproject.movieManagment.database.MovieDB;
import com.finalproject.movieManagment.dtos.MovieDTO;
import com.finalproject.movieManagment.entities.Actor;
import com.finalproject.movieManagment.entities.Movie;
import com.finalproject.movieManagment.entities.MovieEntityAdapter;
import com.finalproject.movieManagment.entities.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.ExportException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class MovieController {
    @Autowired
    private MovieDB movieDB;
    @Autowired
    private ActorDB actorDB;
    @Autowired
    private Mapper mapper;
    @Autowired
    private MovieEntityAdapter movieEntityAdapter;


    @GetMapping("/movies")
    public ResponseEntity<CollectionModel<EntityModel<MovieDTO>>> getAllMovies() {
        List<MovieDTO> movies = mapper.convertMovie(movieDB.findAll());
        List<EntityModel<MovieDTO>> moviesEntityModel = new ArrayList<>();
        for (MovieDTO movieDTO : movies) {
            EntityModel<MovieDTO> movieEntityModel = movieEntityAdapter.toModel(movieDTO);
            moviesEntityModel.add(movieEntityModel);
        }
        return ResponseEntity.ok(new CollectionModel<>(moviesEntityModel,
                linkTo(methodOn(MovieController.class).getAllMovies()).withSelfRel()));
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<EntityModel<MovieDTO>> getMovieById(@PathVariable Long id) {
        MovieDTO movieDTO = new MovieDTO(
                movieDB.findById(id).orElseThrow(() -> new MovieNotFoundException(id)));
        return ResponseEntity.ok(movieEntityAdapter.toModel(movieDTO));
    }


    // Getting id of a movie and returning all the actors
    @GetMapping("/actor/{id}/movies")
    public ResponseEntity<CollectionModel<EntityModel<MovieDTO>>> getMoviesByActor(@PathVariable Long id) {
        Actor actor = actorDB.findById(id).orElseThrow(() ->new ActorNotFoundException(id));
        List<MovieDTO> moviesDTO = mapper.convertMovie(movieDB.findByActors(actor));
        List<EntityModel<MovieDTO>> moviesEntityModel = new ArrayList<>();
        for (MovieDTO movieDTO : moviesDTO) {
            EntityModel<MovieDTO> movieEntityModel = movieEntityAdapter.toModel(movieDTO);
            moviesEntityModel.add(movieEntityModel);
        }
        return ResponseEntity.ok(new CollectionModel<>(moviesEntityModel,
                linkTo(methodOn(MovieController.class).getAllMovies()).withSelfRel()));
    }
    // get a list of movies with a release date is equal or greater then the date provided
    @GetMapping("/movie/date")
    public ResponseEntity<CollectionModel<EntityModel<MovieDTO>>> moviesByDate(@RequestParam(required = true)
    @DateTimeFormat(iso = ISO.DATE)LocalDate date){
        List<MovieDTO> movieDTOs;
        if(date == null) {
            throw new MovieNotFoundException("Date can not be null\nplease enter a valid date Format {YYYY-MM-DD}");
        }else{
            movieDTOs = mapper.convertMovie(movieDB.findAllMovieWithDate(date));
        }
        List<EntityModel<MovieDTO>> movieDTOEntityList = new ArrayList<>();
        for(MovieDTO movieDTO : movieDTOs){
            EntityModel<MovieDTO> movieDTOEntityModel = movieEntityAdapter.toModel(movieDTO);
            movieDTOEntityList.add(movieDTOEntityModel);
        }
        return ResponseEntity.ok(new CollectionModel<>(movieDTOEntityList,
                linkTo(methodOn(MovieController.class).getAllMovies()).withSelfRel()));


    }

    @PostMapping("/movies")
    public ResponseEntity<EntityModel<MovieDTO>> addNewMovie(@RequestBody Movie movie) {
        MovieDTO movieDTO = new MovieDTO(movieDB.save(movie));
        return ResponseEntity.ok(movieEntityAdapter.toModel(movieDTO));
    }

    @DeleteMapping("/movie/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id){
        movieDB.deleteById(id);
        return ResponseEntity.noContent().build();
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