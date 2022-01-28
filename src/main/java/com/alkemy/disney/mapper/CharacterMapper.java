package com.alkemy.disney.mapper;

import com.alkemy.disney.dto.CharacterDto;
import com.alkemy.disney.dto.CharacterDtoMinified;
import com.alkemy.disney.dto.CharacterDtoFull;
import com.alkemy.disney.dto.MovieDto;
import com.alkemy.disney.model.CharacterModel;
import com.alkemy.disney.model.MovieModel;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CharacterMapper {

    public CharacterModel toCharacterModel(CharacterDto characterDto) {
        CharacterModel character = new CharacterModel();
        character.setId(characterDto.getId());
        character.setImage(characterDto.getImage());
        character.setName(characterDto.getName());
        character.setAge(characterDto.getAge());
        character.setWeight(characterDto.getWeight());
        character.setStory(characterDto.getStory());
        return character;
    }

    public CharacterDto toCharacterDto(CharacterModel character) {
        CharacterDto characterDto = new CharacterDto();
        characterDto.setId(character.getId());
        characterDto.setImage(character.getImage());
        characterDto.setName(character.getName());
        characterDto.setAge(character.getAge());
        characterDto.setWeight(character.getWeight());
        characterDto.setStory(character.getStory());
        return characterDto;
    }

    public List<CharacterDtoMinified> toListCharacterDtoMinified(List<CharacterModel> characters) {
        List<CharacterDtoMinified> characterDtos = new ArrayList<>();
        if (characters != null) {
            for (CharacterModel character : characters) {
                CharacterDtoMinified characterDto = new CharacterDtoMinified();
                characterDto.setId(character.getId());
                characterDto.setImage(character.getImage());
                characterDto.setName(character.getName());
                characterDtos.add(characterDto);
            }
        }
        return characterDtos;
    }

    public CharacterDtoFull toCharacterDtoFull(CharacterModel character) {
        CharacterDtoFull characterDto = new CharacterDtoFull();
        characterDto.setId(character.getId());
        characterDto.setImage(character.getImage());
        characterDto.setName(character.getName());
        characterDto.setAge(character.getAge());
        characterDto.setWeight(character.getWeight());
        characterDto.setStory(character.getStory());
        characterDto.setMovies(toSetMovieDto(character.getMovies()));
        return characterDto;
    }

    public Set<MovieDto> toSetMovieDto(Set<MovieModel> movies) {
        Set<MovieDto> movieDtos = new HashSet<>();
        if (movies != null) {
            for (MovieModel movie : movies) {
                MovieDto movieDto = new MovieDto();
                movieDto.setId(movie.getId());
                movieDto.setImage(movie.getImage());
                movieDto.setTitle(movie.getTitle());
                movieDto.setRating(movie.getRating());
                movieDto.setCreationDate(movie.getCreationDate());
                movieDto.setCharacters(new HashSet<>());
                movieDto.setGenres(new HashSet<>());
                movieDtos.add(movieDto);
            }
        }
        return movieDtos;
    }


    public List<CharacterDtoFull> toListCharacterDto(List<CharacterModel> characters) {
        List<CharacterDtoFull> characterDtos = new ArrayList<>();
        if (!characters.isEmpty()) {
            for (CharacterModel character : characters) {
                CharacterDtoFull characterDto = new CharacterDtoFull();
                characterDto.setId(character.getId());
                characterDto.setImage(character.getImage());
                characterDto.setName(character.getName());
                characterDto.setAge(character.getAge());
                characterDto.setWeight(character.getWeight());
                characterDto.setStory(character.getStory());
                characterDto.setMovies(toSetMovieDto(character.getMovies()));
                characterDtos.add(characterDto);
            }
        }
        return characterDtos;
    }

}
