package com.alkemy.disney.controller;

import com.alkemy.disney.dto.CharacterDto;
import com.alkemy.disney.dto.CharacterDtoMinified;
import com.alkemy.disney.dto.CharacterDtoFull;
import com.alkemy.disney.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    /*
    3. Listado de Personajes
        El listado deberá mostrar:
            ● Imagen.
            ● Nombre.

        El endpoint deberá ser:
            ● /characters
     */

    // GetAll
    @GetMapping(path = "/all")
    public ResponseEntity<List<CharacterDtoMinified>> getAllCharacters() {
        List<CharacterDtoMinified> characters = characterService.getAllCharacters();
        return ResponseEntity.ok(characters);
    }

    /*
    4. Creación, Edición y Eliminación de Personajes (CRUD)
        Deberán existir las operaciones básicas de creación, edición y eliminación de personajes.
     */

    //Character creation
    @PostMapping
    public ResponseEntity<CharacterDto> saveCharacter(@RequestBody CharacterDto characterDto) {
        CharacterDto character = characterService.saveCharacter(characterDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(character);
    }

    //Character update
    @PutMapping
    public ResponseEntity<CharacterDto> updateCharacter(@RequestBody CharacterDto characterDto) {
        CharacterDto character = characterService.updateCharacter(characterDto);
        return ResponseEntity.ok(character);
    }

    //Character elimination
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable Long id) throws SQLException {
        characterService.deleteCharacter(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /*
    5. Detalle de Personaje
        En el detalle deberán listarse todos los atributos del personaje, como así también sus películas o
        series relacionadas.
     */

    // GetById
    @GetMapping(path = "/{id}")
    public ResponseEntity<CharacterDtoFull> getCharacter(@PathVariable Long id) {
        CharacterDtoFull character = characterService.getCharacter(id);
        return ResponseEntity.ok(character);
    }

    /*
    6. Búsqueda de Personajes
        Deberá permitir buscar por nombre, y filtrar por edad, peso o películas/series en las que participó.
        Para especificar el término de búsqueda o filtros se deberán enviar como parámetros de query:
            ● GET /characters?name=nombre
            ● GET /characters?age=edad
            ● GET /characters?movies=idMovie
     */

    // GetByFilters
    @GetMapping
    public ResponseEntity<List<CharacterDtoFull>> getCharactersByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Short age,
            @RequestParam(required = false) Set<Long> movies) {
        List<CharacterDtoFull> characters = characterService.getCharactersByFilters(name, age, movies);
        return ResponseEntity.ok(characters);
    }
}
