package com.finalproject.movieManagment.database;

import com.finalproject.movieManagment.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieDB extends JpaRepository<Movie, String> {
    List<Movie> findTop1000By();
    //List<Movie> findByPrimaryTitle(String actor);
}
