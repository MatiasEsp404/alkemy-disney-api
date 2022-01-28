package com.alkemy.disney.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "movies")
@Getter
@Setter
public class MovieModel {

    /*
    1. Modelado de Base de Datos
    Película o Serie: deberá tener,
        ○ Imagen.
        ○ Título.
        ○ Fecha de creación.
        ○ Calificación (del 1 al 5).
        ○ Personajes asociados.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;
    private String title;
    @Column(name = "creation_date")
    private Date creationDate;
    private Float rating;

    // Movies engloba a Characters -> Cuando creo una pelicula puedo pasarle una
    // lista de
    // personajes y se va a crear la relación de ManyToMany pero cuando creo un
    // personaje
    // no le voy a poder pasar una lista de peliculas para que lo cree.
    // Esto pasa porque quien toma el ownership de la relación es la pelicula.
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @ManyToMany()
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinTable(
            name = "character_movie",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    Set<CharacterModel> characters = new HashSet<>();

    // Una pelicula o serie puede tener muchos generos.
    // Ej: La familia de mi novia -> Comedia / Romance
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @ManyToMany()
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinTable(
            name = "genre_movie",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    Set<GenreModel> genres = new HashSet<>();

}
