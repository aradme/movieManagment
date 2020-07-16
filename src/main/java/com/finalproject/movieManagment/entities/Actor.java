package com.finalproject.movieManagment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "actors")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actorName;

    private LocalDate birthYear;

    private LocalDate deathYear;

    private String knownFor;

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies;
}
