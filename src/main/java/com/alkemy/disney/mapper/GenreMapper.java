package com.alkemy.disney.mapper;

import com.alkemy.disney.dto.*;
import com.alkemy.disney.model.GenreModel;
import com.alkemy.disney.model.MovieModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GenreMapper {

    public GenreModel toGenreModel(GenreDto genreDto) {
        GenreModel genre = new GenreModel();
        genre.setId(genreDto.getId());
        genre.setName(genreDto.getName());
        genre.setImage(genreDto.getImage());
        return genre;
    }

    public GenreDto toGenreDto(GenreModel genre) {
        GenreDto genreDto = new GenreDto();
        if (genre != null) {
            genreDto.setId(genre.getId());
            genreDto.setImage(genre.getImage());
            genreDto.setName(genre.getName());
        }
        return genreDto;
    }

    public List<GenreDtoMinified> toListGenreDtoMinified(List<GenreModel> genres) {
        List<GenreDtoMinified> genreDtos = new ArrayList<>();
        if (genres!= null){
            for(GenreModel genre: genres){
                GenreDtoMinified genreDto = new GenreDtoMinified();
                genreDto.setId(genre.getId());
                genreDto.setImage(genre.getImage());
                genreDto.setName(genre.getName());
                genreDtos.add(genreDto);
            }
        }
        return genreDtos;
    }

    public GenreDtoFull toGenreDtoFull(GenreModel genre) {
        GenreDtoFull genreDto = new GenreDtoFull();
        genreDto.setId(genre.getId());
        genreDto.setImage(genre.getImage());
        genreDto.setName(genre.getName());
        genreDto.setMovies(toSetMovieDto(genre.getMovies()));
        return genreDto;
    }

    public Set<MovieDto> toSetMovieDto(Set<MovieModel> movies) {
        Set<MovieDto> movieDtos = new HashSet<>();
        if(movies!=null){
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
}
