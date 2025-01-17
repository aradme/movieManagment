package com.finalproject.movieManagment.controllers;

import com.finalproject.movieManagment.database.ActorDB;
import com.finalproject.movieManagment.database.MovieDB;
import com.finalproject.movieManagment.dtos.ActorDTO;
import com.finalproject.movieManagment.dtos.MovieDTO;
import com.finalproject.movieManagment.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ActorController {

    @Autowired
    private MovieDB movieDB;
    @Autowired
    private ActorDB actorDB;
    @Autowired
    private Mapper mapper;
    @Autowired
    private ActorEntityAdapter actorEntityAdapter;


    @GetMapping("/actors")
    public ResponseEntity<CollectionModel<EntityModel<ActorDTO>>> getAllActors() {
        List<ActorDTO> actors = mapper.convertActors(actorDB.findAll());
        List<EntityModel<ActorDTO>> actorsEntityModel = new ArrayList<>();
        for (ActorDTO actorDTO : actors) {
            EntityModel<ActorDTO> actorEntityModel = actorEntityAdapter.toModel(actorDTO);
            actorsEntityModel.add(actorEntityModel);
        }
        return ResponseEntity.ok(new CollectionModel<>(actorsEntityModel,
                linkTo(methodOn(ActorController.class).getAllActors()).withSelfRel()));
    }

    @GetMapping("/actor/{id}")
    public ResponseEntity<EntityModel<ActorDTO>> getActorById(@PathVariable Long id) {
        ActorDTO actorDTO = new ActorDTO(
                actorDB.findById(id).orElseThrow(() -> new ActorNotFoundException(id)));
        return ResponseEntity.ok(actorEntityAdapter.toModel(actorDTO));
    }

    // Getting id of a movie and returning all the actors
    @GetMapping("/movie/{id}/actors")
    public ResponseEntity<CollectionModel<EntityModel<ActorDTO>>> getActorsByMovie(@PathVariable Long id) {
        Movie movie = movieDB.findById(id).orElseThrow(() ->new ActorNotFoundException(id));
        List<ActorDTO> actorsDTO = mapper.convertActors(actorDB.findByMovies(movie));
        List<EntityModel<ActorDTO>> moviesEntityModel = new ArrayList<>();
        for (ActorDTO actorDTO : actorsDTO) {
            EntityModel<ActorDTO> actorEntityModel = actorEntityAdapter.toModel(actorDTO);
            moviesEntityModel.add(actorEntityModel);
        }
        return ResponseEntity.ok(new CollectionModel<>(moviesEntityModel,
                linkTo(methodOn(ActorController.class).getAllActors()).withSelfRel()));
    }
    // returning all the actors that there birthDate is bigger then provided.
    @GetMapping("/actors/date")
    public ResponseEntity<CollectionModel<EntityModel<ActorDTO>>> moviesByDate(@RequestParam(required = true)
           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        List<ActorDTO> actorDTOs;
        if(date == null) {
            throw new ActorNotFoundException("Date can not be null\nplease enter a valid date Format {YYYY-MM-DD}");
        }else{
            actorDTOs = mapper.convertActors(actorDB.findAllActorsWithDate(date));
        }
        List<EntityModel<ActorDTO>> actorDTOEntityList = new ArrayList<>();
        for(ActorDTO actorDTO : actorDTOs){
            EntityModel<ActorDTO> actorDTOEntityModel = actorEntityAdapter.toModel(actorDTO);
            actorDTOEntityList.add(actorDTOEntityModel);
        }
        return ResponseEntity.ok(new CollectionModel<>(actorDTOEntityList,
                linkTo(methodOn(ActorController.class).getAllActors()).withSelfRel()));
    }
    // returning all the actors that play in a movie that the movie rate is bigger then provided.
    @GetMapping("/actors/movies/rating")
    public ResponseEntity<CollectionModel<EntityModel<ActorDTO>>> getActorsWithMovieAndRatings(@RequestParam(required = true)
                                                                                                           Double rate){
        List<ActorDTO> actorDTOs;
        if(rate == null) {
            throw new ActorNotFoundException("Rate can not be null\nplease enter a valid rate");
        }else{
            actorDTOs = mapper.convertActors(actorDB.findALlActorsThatPlayOnMovieWithRate(rate));
        }
        List<EntityModel<ActorDTO>> actorDTOEntityList = new ArrayList<>();
        for(ActorDTO actorDTO : actorDTOs){
            EntityModel<ActorDTO> actorDTOEntityModel = actorEntityAdapter.toModel(actorDTO);
            actorDTOEntityList.add(actorDTOEntityModel);
        }
        return ResponseEntity.ok(new CollectionModel<>(actorDTOEntityList,
                linkTo(methodOn(ActorController.class).getAllActors()).withSelfRel()));
    }

    @PostMapping("/actors")
    public ResponseEntity<EntityModel<ActorDTO>> addNewActor(@RequestBody Actor actor) {
        ActorDTO actorDTO = new ActorDTO(actorDB.save(actor));
        return ResponseEntity.ok(actorEntityAdapter.toModel(actorDTO));
    }

    @PutMapping("/actor/{id}/deathDate")
    public ResponseEntity<EntityModel<ActorDTO>> updateNewDeathDateToActor(@RequestBody Actor actor, @PathVariable Long id) {

        Actor death = actorDB.findById(id).orElseThrow(() -> new ActorNotFoundException(id));
        death.setDeathYear(actor.getDeathYear());
        ActorDTO actorDTO = new ActorDTO(actorDB.save(death));
        return ResponseEntity.ok(actorEntityAdapter.toModel(actorDTO));
    }

    @DeleteMapping("/actor/{id}")
    public ResponseEntity<?> deleteActors(@PathVariable Long id){
        actorDB.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
