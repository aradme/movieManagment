package com.finalproject.movieManagment.database;

import com.finalproject.movieManagment.entities.Actor;
import com.finalproject.movieManagment.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActorDB extends JpaRepository<Actor, Long> {


    List<Actor> findByMovies(Movie movie);
}
