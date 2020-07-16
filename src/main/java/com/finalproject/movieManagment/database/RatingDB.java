package com.finalproject.movieManagment.database;

import com.finalproject.movieManagment.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingDB extends JpaRepository<Rating,Long> {

}
