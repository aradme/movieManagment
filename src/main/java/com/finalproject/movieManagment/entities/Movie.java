package com.finalproject.movieManagment.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String movieTitle;

    private LocalDate ReleaseDate;

    private double duration;

    private String genre;

    @ManyToOne
    private Rating rating;

    @ManyToMany
    @JoinTable(name = "movies_actors", joinColumns = {
            @JoinColumn(name = "movies_id") },
            inverseJoinColumns = {
                    @JoinColumn(name = "actors_id") })
    private List<Actor> actors;

}
