package com.finalproject.movieManagment.controllers;

import com.finalproject.movieManagment.database.RatingDB;
import com.finalproject.movieManagment.dtos.MovieDTO;
import com.finalproject.movieManagment.dtos.RatingDTO;
import com.finalproject.movieManagment.entities.Mapper;
import com.finalproject.movieManagment.entities.Movie;
import com.finalproject.movieManagment.entities.Rating;
import com.finalproject.movieManagment.entities.RatingEntityAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RatingController {
    @Autowired
    private RatingDB ratingDB;
    @Autowired
    private RatingEntityAdapter ratingEntityAdapter;
    @Autowired
    private Mapper mapper;

    @GetMapping("/movie/{id}/rating")
    public ResponseEntity<EntityModel<RatingDTO>> getRatingByMovie(@PathVariable Long id) {
        RatingDTO ratingDTO = new RatingDTO(
                ratingDB.findById(id).orElseThrow(() -> new RatingNotFoundException(id)));
        return ResponseEntity.ok(ratingEntityAdapter.toModel(ratingDTO));
    }

    @GetMapping("/ratings")
    public ResponseEntity<CollectionModel<EntityModel<RatingDTO>>> getAllRatings() {
        List<RatingDTO> ratings = mapper.convertRating(ratingDB.findAll());
        List<EntityModel<RatingDTO>> ratingsEntityModel = new ArrayList<>();
        for (RatingDTO ratingDTO : ratings) {
            EntityModel<RatingDTO> ratingEntityModel = ratingEntityAdapter.toModel(ratingDTO);
            ratingsEntityModel.add(ratingEntityModel);
        }
        return ResponseEntity.ok(new CollectionModel<>(ratingsEntityModel,
                linkTo(methodOn(MovieController.class).getAllMovies()).withSelfRel()));
    }

    @PostMapping("/rate")
    public ResponseEntity<EntityModel<RatingDTO>> addNewRating(@RequestBody Rating rating) {
        RatingDTO ratingDTO = new RatingDTO(ratingDB.save(rating));
        return ResponseEntity.ok(ratingEntityAdapter.toModel(ratingDTO));
    }

    @PutMapping("/rate/movie/{id}")
    public ResponseEntity<EntityModel<RatingDTO>> addNewRatingToMovie(@RequestBody Rating rating,@PathVariable Long id) {
        try{
            //if the findById doesn't work the getOne is working
            Rating newRate = ratingDB.findById(id).orElseThrow(() -> new RatingNotFoundException(id));
            if(newRate.getRate()<=10 && newRate.getRate()<rating.getRate() && rating.getRate() <=10){
                //newRate.setRate(rating.getRate()/(newRate.getVotes()+1) + newRate.getRate());
                newRate.setRate(((newRate.getRate()*newRate.getVotes())+rating.getRate())/(newRate.getVotes() + 1));
                newRate.setVotes(newRate.getVotes() + 1);
            }/*else if(newRate.getRate()<=10 && newRate.getRate()>rating.getRate()){
                newRate.setRate(rating.getRate()/(newRate.getVotes()+1) - newRate.getRate());
                newRate.setVotes(newRate.getVotes() + 1);
            }*/else{
                throw new RatingNotFoundException("Rate can not be bigger then 10 \n   Please enter a new Rate.");
            }
            RatingDTO ratingDTO = new RatingDTO(ratingDB.save(newRate));
            return ResponseEntity.ok(ratingEntityAdapter.toModel(ratingDTO));
        }catch (Exception e){
            throw new RatingNotFoundException("Rate can not be bigger then 10 or Null! \n Please enter a new Rate.");
        }
    }

    /*@DeleteMapping("/rate/movie/{id}")
    public ResponseEntity<EntityModel<RatingDTO>> deleteRate(@PathVariable Long id) {
        Rating newRate = ratingDB.getOne(id);
        newRate.setRate(((newRate.getRate()*newRate.getVotes())-newRate.getRate())/(newRate.getVotes()-1));
        newRate.setVotes(newRate.getVotes() - 1);
        RatingDTO ratingDTO = new RatingDTO(ratingDB.save(newRate));
        return ResponseEntity.ok(ratingEntityAdapter.toModel(ratingDTO));
    }*/


}
