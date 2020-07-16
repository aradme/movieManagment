package com.finalproject.movieManagment.database;

import com.finalproject.movieManagment.entities.Actor;
import com.finalproject.movieManagment.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MovieDB extends JpaRepository<Movie, Long> {

    List<Movie> findByActors(Actor actor);
    @Query(nativeQuery = true, value = "select * from movies where movies.ReleaseDate >= :fromDate")
    List<Movie> findAllMovieWithDate(@Param("fromDate")LocalDate fromDate);
}
