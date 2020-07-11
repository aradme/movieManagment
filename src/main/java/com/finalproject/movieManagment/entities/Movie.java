package com.finalproject.movieManagment.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "listofmovies")
public class Movie {

    @Id
    @GeneratedValue
    private String tconst;

    private String titleType;

    private String originalTitle;

    private String primaryTitle;

    private double isAdult;

    private double startYear;

    private String endYear;

    private double runtimeMinutes;

    private String genres;

    @ManyToMany(mappedBy = "movies")
    private List<Actor> actors;

}
