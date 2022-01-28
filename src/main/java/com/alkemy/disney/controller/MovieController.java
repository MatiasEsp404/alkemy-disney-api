package com.alkemy.disney.controller;

import com.alkemy.disney.dto.MovieDto;
import com.alkemy.disney.dto.MovieDtoFull;
import com.alkemy.disney.dto.MovieDtoMinified;
import com.alkemy.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    /*
    7. Listado de Películas
    Deberá mostrar solamente los campos imagen, título y fecha de creación.
    El endpoint deberá ser:
        ● GET /movies
     */

    // Get all
    @GetMapping(path = "/all")
    public ResponseEntity<List<MovieDtoMinified>> getAllMovies() {
        List<MovieDtoMinified> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    /*
    8. Detalle de Película / Serie con sus personajes
        Devolverá todos los campos de la película o serie junto a los personajes asociados a la misma
     */

    // Get by Id
    @GetMapping(path = "/{id}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable Long id) {
        MovieDto movie = movieService.getMovie(id);
        return ResponseEntity.ok(movie);
    }

    /*
    9. Creación, Edición y Eliminación de Película / Serie
        Deberán existir las operaciones básicas de creación, edición y eliminación de películas o series.
     */

    // Creation
    @PostMapping
    public ResponseEntity<Void> saveMovie(@RequestBody MovieDto movieDto) {
        movieService.saveMovie(movieDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Update
    @PutMapping
    public ResponseEntity<MovieDto> updateMovie(@RequestBody MovieDto movieDto) throws SQLException {
        MovieDto movie = movieService.updateMovie(movieDto);
        return ResponseEntity.ok(movie);
    }

    // Delete
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }

    // Add character to a movie
    @PostMapping(path = "/{movieId}/character/{characterId}")
    public ResponseEntity<Void> addCharacterToMovie(@PathVariable Long characterId, @PathVariable Long movieId) {
        movieService.addCharacterToMovie(characterId, movieId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Remove character from a movie
    @DeleteMapping(path = "/{movieId}/character/{characterId}")
    public ResponseEntity<Void> removeCharacterFromMovie(@PathVariable Long characterId, @PathVariable Long movieId) {
        movieService.removeCharacterFromMovie(characterId, movieId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Add genre to a movie
    @PostMapping(path = "/{movieId}/genre/{genreId}")
    public ResponseEntity<Void> addGenreToMovie(@PathVariable Long genreId, @PathVariable Long movieId) {
        movieService.addGenreToMovie(genreId, movieId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Remove genre from a movie
    @DeleteMapping(path = "/{movieId}/genre/{genreId}")
    public ResponseEntity<Void> removeGenreFromMovie(@PathVariable Long genreId, @PathVariable Long movieId) {
        movieService.removeGenreFromMovie(genreId, movieId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /*
    10.Búsqueda de Películas o Series
        Deberá permitir buscar por título, y filtrar por género. Además, permitir ordenar los resultados por
        fecha de creación de forma ascendiente o descendiente.
        El término de búsqueda, filtro u ordenación se deberán especificar como parámetros de query:
            ● /movies?name=nombre
            ● /movies?genre=idGenero
            ● /movies?order=ASC | DESC
     */

    // GetByFilters
    @GetMapping
    public ResponseEntity<List<MovieDtoFull>> getMoviesByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Set<Long> genres,
            @RequestParam(required = false, defaultValue = "ASC") String order) {
        List<MovieDtoFull> movies = movieService.getMoviesByFilters(name, genres, order);
        return ResponseEntity.ok(movies);
    }

}