package com.alkemy.disney.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "characters")
@Getter
@Setter
public class CharacterModel {

    /*
    1. Modelado de Base de Datos
    Personaje: deberá tener,
        ○ Imagen.
        ○ Nombre.
        ○ Edad.
        ○ Peso.
        ○ Historia.
        ○ Películas o series asociadas.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String name;
    private Short age;
    private Float weight;
    private String story;

    @ManyToMany(mappedBy = "characters", fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    private Set<MovieModel> movies = new HashSet<>();

}
