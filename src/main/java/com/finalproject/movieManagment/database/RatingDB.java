package com.finalproject.movieManagment.database;

import com.finalproject.movieManagment.entities.Movie;
import com.finalproject.movieManagment.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RatingDB extends JpaRepository<Rating,Long> {
//TODO: need three functions???
    @Query(nativeQuery = true, value = "select rating.* from rating join movies on rating.id = movies.rating_id and movies.ReleaseDate >= :fromDate")
    List<Rating> findAllRatingWithDate(@Param("fromDate") LocalDate fromDate);

}
