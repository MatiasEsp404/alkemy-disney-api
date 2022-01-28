package com.alkemy.disney.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "genres")
@Getter
@Setter
public class GenreModel {

    /*
    1. Modelado de Base de Datos
    Género: deberá tener,
        ○ Nombre.
        ○ Imagen.
        ○ Películas o series asociadas.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String image;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    private Set<MovieModel> movies = new HashSet<>();

}
