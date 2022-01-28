package com.alkemy.disney.mapper;

import com.alkemy.disney.dto.*;
import com.alkemy.disney.model.CharacterModel;
import com.alkemy.disney.model.GenreModel;
import com.alkemy.disney.model.MovieModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MovieMapper {

    public List<MovieDtoMinified> toListMovieDtoMinified(List<MovieModel> movies) {
        List<MovieDtoMinified> movieDtos = new ArrayList<>();
        if (movies != null) {
            for (MovieModel movie : movies) {
                MovieDtoMinified movieDto = new MovieDtoMinified();
                movieDto.setId(movie.getId());
                movieDto.setImage(movie.getImage());
                movieDto.setTitle(movie.getTitle());
                movieDto.setCreationDate(movie.getCreationDate());
                movieDtos.add(movieDto);
            }
        }
        return movieDtos;
    }

    public MovieDto toMovieDto(MovieModel movie) {
        MovieDto movieDto = new MovieDto();
        if(movie!=null) {
            movieDto.setId(movie.getId());
            movieDto.setImage(movie.getImage());
            movieDto.setTitle(movie.getTitle());
            movieDto.setRating(movie.getRating());
            movieDto.setCreationDate(movie.getCreationDate());
            movieDto.setCharacters(toSetCharacterDto(movie.getCharacters()));
            movieDto.setGenres(toSetGenreDto(movie.getGenres()));
        }
        return movieDto;
    }

    private Set<CharacterDto> toSetCharacterDto(Set<CharacterModel> characters) {
        Set<CharacterDto> characterDtos = new HashSet<>();
        for (CharacterModel character : characters) {
            CharacterDto characterDto = new CharacterDto();
            characterDto.setId(character.getId());
            characterDto.setImage(character.getImage());
            characterDto.setName(character.getName());
            characterDto.setAge(character.getAge());
            characterDto.setWeight(character.getWeight());
            characterDto.setStory(character.getStory());
            characterDtos.add(characterDto);
        }
        return characterDtos;
    }

    private Set<GenreDto> toSetGenreDto(Set<GenreModel> genres) {
        Set<GenreDto> genreDtos = new HashSet<>();
        if (genres != null) {
            for (GenreModel genre : genres) {
                GenreDto genreDto = new GenreDto();
                genreDto.setId(genre.getId());
                genreDto.setImage(genre.getImage());
                genreDto.setName(genre.getName());
                genreDtos.add(genreDto);
            }
        }
        return genreDtos;
    }

    public MovieModel toMovieModel(MovieDto movieDto) {
        MovieModel movie = new MovieModel();
        movie.setId(movieDto.getId());
        movie.setImage(movieDto.getImage());
        movie.setTitle(movieDto.getTitle());
        movie.setRating(movieDto.getRating());
        movie.setCreationDate(movieDto.getCreationDate());
        movie.setCharacters(toSetCharacterModel(movieDto.getCharacters()));
        movie.setGenres(toSetGenreModel(movieDto.getGenres()));
        return movie;
    }

    private Set<CharacterModel> toSetCharacterModel(Set<CharacterDto> characters) {
        Set<CharacterModel> characterSet = new HashSet<>();
        for (CharacterDto characterDto : characters) {
            CharacterModel character = new CharacterModel();
            character.setId(characterDto.getId());
            character.setImage(characterDto.getImage());
            character.setName(characterDto.getName());
            character.setAge(characterDto.getAge());
            character.setWeight(characterDto.getWeight());
            character.setStory(characterDto.getStory());
            characterSet.add(character);
        }
        return characterSet;
    }

    private Set<GenreModel> toSetGenreModel(Set<GenreDto> genres) {
        Set<GenreModel> genreSet=new HashSet<>();
        for(GenreDto genreDto : genres) {
            GenreModel genre = new GenreModel();
            genre.setId(genreDto.getId());
            genre.setName(genreDto.getName());
            genre.setImage(genreDto.getImage());
            genreSet.add(genre);
        }
        return genreSet;
    }

    public List<MovieDtoFull> toListMovieDtoFull(List<MovieModel> movies) {
        List<MovieDtoFull> movieDtos = new ArrayList<>();
        if (!movies.isEmpty()) {
            for (MovieModel movie : movies) {
                MovieDtoFull movieDto = new MovieDtoFull();
                movieDto.setId(movie.getId());
                movieDto.setImage(movie.getImage());
                movieDto.setTitle(movie.getTitle());
                movieDto.setCreationDate(movie.getCreationDate());
                movieDto.setRating(movie.getRating());
                movieDto.setCharacters(toSetCharacterDto(movie.getCharacters()));
                movieDto.setGenres(toSetGenreDto(movie.getGenres()));
                movieDtos.add(movieDto);
            }
        }
        return movieDtos;
    }

}
