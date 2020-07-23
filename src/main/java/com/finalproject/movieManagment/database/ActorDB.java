package com.finalproject.movieManagment.database;

import com.finalproject.movieManagment.entities.Actor;
import com.finalproject.movieManagment.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ActorDB extends JpaRepository<Actor, Long> {

    List<Actor> findByMovies(Movie movie);
    @Query(nativeQuery = true, value = "select * from actors where actors.birthYear <= :fromDate")
    List<Actor> findAllActorsWithDate(@Param("fromDate") LocalDate fromDate);
    @Query(nativeQuery = true, value = "select actors.* from actors inner join movies_actors inner join movies inner join rating on actors.id=actors_id and movies.id=movies_id and movies.rating_id = rating.id and rating.rate > :rate")
    List<Actor> findALlActorsThatPlayOnMovieWithRate(Double rate);
}
