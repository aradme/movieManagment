package com.finalproject.movieManagment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "actors")
public class Actor {

    @Id
    @GeneratedValue
    private String nconst;

    private String primaryName;

    private double birthYear;

    private double deathYear;

    private String primaryProfession;

    private String knownForTitles;

    @ManyToMany
    @JoinTable(name = "movie_actors", joinColumns = {
            @JoinColumn(name = "actor_id") },
            inverseJoinColumns = {
            @JoinColumn(name = "movie_id") })
    private List<Movie> movies;
}
