package com.finalproject.movieManagment.controllers;

import com.finalproject.movieManagment.database.MovieDB;
import com.finalproject.movieManagment.database.RatingDB;
import com.finalproject.movieManagment.dtos.MovieDTO;
import com.finalproject.movieManagment.dtos.RatingDTO;
import com.finalproject.movieManagment.entities.Mapper;
import com.finalproject.movieManagment.entities.Rating;
import com.finalproject.movieManagment.entities.RatingEntityAdapter;
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
public class RatingController {
    @Autowired
    private RatingDB ratingDB;
    @Autowired
    private RatingEntityAdapter ratingEntityAdapter;
    @Autowired
    private MovieDB movieDB;
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
                linkTo(methodOn(RatingController.class).getAllRatings()).withSelfRel()));
    }


    // returning all the rating that there movie's release date is bigger then
    @GetMapping("/rating/date")
    public ResponseEntity<CollectionModel<EntityModel<RatingDTO>>> getRatingByMovieDate(@RequestParam(required = true)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        List<RatingDTO> ratingDTOs;
        if(date == null) {
            throw new RatingNotFoundException("Date can not be null\nplease enter a valid date Format {YYYY-MM-DD}");
        }else{
            ratingDTOs = mapper.convertRating(ratingDB.findAllRatingWithDate(date));
        }
        List<EntityModel<RatingDTO>> ratingDTOEntityList = new ArrayList<>();
        for(RatingDTO movieDTO : ratingDTOs){
            EntityModel<RatingDTO> ratingDTOEntityModel = ratingEntityAdapter.toModel(movieDTO);
            ratingDTOEntityList.add(ratingDTOEntityModel);
        }
        return ResponseEntity.ok(new CollectionModel<>(ratingDTOEntityList,
                linkTo(methodOn(RatingController.class).getAllRatings()).withSelfRel()));
    }
    @PostMapping("/rate")
    public ResponseEntity<EntityModel<RatingDTO>> addNewRating(@RequestBody Rating rating) {
        RatingDTO ratingDTO = new RatingDTO(ratingDB.save(rating));
        return ResponseEntity.ok(ratingEntityAdapter.toModel(ratingDTO));
    }
    // updating rate to a movie
    @PutMapping("/rate/movie/{id}")
    public ResponseEntity<EntityModel<RatingDTO>> updateRatingToMovie(@RequestBody Rating rating,@PathVariable Long id) {
        try{
            //if the findById doesn't work the getOne is working
            Rating newRate = ratingDB.findById(id).orElseThrow(() -> new RatingNotFoundException(id));
            if(newRate.getRate()<=10 && newRate.getRate()<rating.getRate() && rating.getRate() <=10){
                newRate.setRate(((newRate.getRate()*newRate.getVotes())+rating.getRate())/(newRate.getVotes() + 1));
                newRate.setVotes(newRate.getVotes() + 1);
            }else{
                throw new RatingNotFoundException("Rate can not be bigger then 10\n\tPlease enter a new Rate.");
            }
            RatingDTO ratingDTO = new RatingDTO(ratingDB.save(newRate));
            return ResponseEntity.ok(ratingEntityAdapter.toModel(ratingDTO));
        }catch (Exception e){
            throw new RatingNotFoundException("Rate can not be Null!\n\tPlease enter a new Rate.");
        }
    }

    // delete rating by is ID
    @DeleteMapping("/rating/{id}")
    public ResponseEntity<?> deleteRating(@PathVariable Long id){
        ratingDB.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
