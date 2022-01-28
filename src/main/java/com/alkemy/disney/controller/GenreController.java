package com.alkemy.disney.controller;

import com.alkemy.disney.dto.GenreDto;
import com.alkemy.disney.dto.GenreDtoMinified;
import com.alkemy.disney.dto.GenreDtoFull;
import com.alkemy.disney.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(path = "/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    // Create
    @PostMapping
    public ResponseEntity<GenreDto> saveGenre(@RequestBody GenreDto genreDto) {
        GenreDto genre = genreService.saveGenre(genreDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(genre);
    }

    // Update
    @PutMapping
    public ResponseEntity<GenreDto> updateGenre(@RequestBody GenreDto genreDto) {
        GenreDto genre = genreService.updateGenre(genreDto);
        return ResponseEntity.ok(genre);
    }

    // Delete
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) throws SQLException {
        genreService.deleteGenre(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Get by id
    @GetMapping(path = "/{id}")
    public ResponseEntity<GenreDtoFull> getGenre(@PathVariable Long id) {
        GenreDtoFull genre = genreService.getGenre(id);
        return ResponseEntity.ok(genre);
    }

    // Get all
    @GetMapping(path = "/all")
    public ResponseEntity<List<GenreDtoMinified>> getAllGenres() {
        List<GenreDtoMinified> genres = genreService.getAllGenres();
        return ResponseEntity.ok(genres);
    }
}
