package com.alkemy.disney.service;

import com.alkemy.disney.dto.*;
import com.alkemy.disney.exception.MovieException;
import com.alkemy.disney.exception.msg.ExceptionMsg;
import com.alkemy.disney.mapper.MovieMapper;
import com.alkemy.disney.model.CharacterModel;
import com.alkemy.disney.model.GenreModel;
import com.alkemy.disney.model.MovieModel;
import com.alkemy.disney.repository.CharacterRepository;
import com.alkemy.disney.repository.GenreRepository;
import com.alkemy.disney.repository.MovieRepository;
import com.alkemy.disney.repository.specifications.MovieSpecification;
import com.alkemy.disney.validator.DtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private DtoValidator dtoValidator;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieSpecification movieSpecification;

    // Get all
    public List<MovieDtoMinified> getAllMovies() {
        List<MovieModel> movies = movieRepository.findAll();
        return movieMapper.toListMovieDtoMinified(movies);
    }

    // Get by Id
    public MovieDto getMovie(Long id) {
        MovieModel movie = movieRepository.getById(id);
        return movieMapper.toMovieDto(movie);
    }

    // Creation
    public void saveMovie(MovieDto movieDto) {
        if (dtoValidator.movieDtoIsValid(movieDto)) {
            MovieModel movie = movieMapper.toMovieModel(movieDto);
            Set<CharacterModel> characters = movie.getCharacters();
            Set<GenreModel> genres = movie.getGenres();
            movie.setCharacters(characterFilter(characters, false));
            movie.setGenres(genreFilter(genres, false));
            movie = movieRepository.save(movie);
            assignExistingCharacters(characterFilter(characters, true), movie.getId());
            assignExistingGenres(genreFilter(genres, true), movie.getId());
        } else {
            throw new MovieException(ExceptionMsg.DTO_WRONG_DATA);
        }
    }

    private Set<CharacterModel> characterFilter(Set<CharacterModel> characters, boolean existing) {
        Set<CharacterModel> res = new HashSet<>();
        for (CharacterModel character : characters) {
            if (character.getId() == null) {
                if (!existing)
                    res.add(character);
            } else {
                if (characterRepository.existsById(character.getId())) {
                    if (existing)
                        res.add(character);
                } else {
                    throw new MovieException(ExceptionMsg.CHARACTER_NOT_FOUND);
                }
            }
        }
        return res;
    }

    private void assignExistingCharacters(Set<CharacterModel> characters, Long movieId) {
        for (CharacterModel character : characters) {
            addCharacterToMovie(character.getId(), movieId);
        }
    }

    private Set<GenreModel> genreFilter(Set<GenreModel> genres, boolean existing) {
        Set<GenreModel> res = new HashSet<>();
        for (GenreModel genre : genres) {
            if (genre.getId() == null) {
                if (!existing)
                    res.add(genre);
            } else {
                if (genreRepository.existsById(genre.getId())) {
                    if (existing)
                        res.add(genre);
                } else {
                    throw new MovieException(ExceptionMsg.GENRE_NOT_FOUND);
                }
            }
        }
        return res;
    }

    private void assignExistingGenres(Set<GenreModel> genres, Long movieId) {
        for (GenreModel genre : genres) {
            addGenreToMovie(genre.getId(), movieId);
        }
    }

    // Update
    public MovieDto updateMovie(MovieDto movieDto) {
        if (movieRepository.existsById(movieDto.getId())) {
            if (dtoValidator.movieDtoToUpdateIsValid(movieDto)) {
                MovieModel movie = movieRepository.getById(movieDto.getId());
                movie.setId(movieDto.getId());
                movie.setImage(movieDto.getImage());
                movie.setTitle(movieDto.getTitle());
                movie.setRating(movieDto.getRating());
                movie.setCreationDate(movieDto.getCreationDate());
                movie = movieRepository.save(movie);
                return movieMapper.toMovieDto(movie);
            } else {
                throw new MovieException(ExceptionMsg.DTO_WRONG_DATA);
            }
        } else {
            throw new MovieException(ExceptionMsg.MOVIE_NOT_FOUND);
        }
    }

    // Delete
    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new EntityNotFoundException(ExceptionMsg.MOVIE_NOT_FOUND);
        }
        movieRepository.deleteById(id);
    }

    // Add character to a movie
    public void addCharacterToMovie(Long characterId, Long movieId) {
        if (characterRepository.existsById(characterId) &&
                movieRepository.existsById(movieId)) {
            MovieModel movie = movieRepository.getById(movieId);
            CharacterModel character = characterRepository.getById(characterId);
            Set<CharacterModel> characters = movie.getCharacters();
            characters.add(character);
            movie.setCharacters(characters);
            movieRepository.save(movie);
        } else if (!characterRepository.existsById(characterId)) {
            throw new EntityNotFoundException(ExceptionMsg.CHARACTER_NOT_FOUND);
        } else if (!movieRepository.existsById(movieId)) {
            throw new EntityNotFoundException(ExceptionMsg.MOVIE_NOT_FOUND);
        }
    }

    // Remove character from a movie
    public void removeCharacterFromMovie(Long characterId, Long movieId) {
        if (characterRepository.existsById(characterId) &&
                movieRepository.existsById(movieId)) {
            MovieModel movie = movieRepository.getById(movieId);
            CharacterModel character = characterRepository.getById(characterId);
            Set<CharacterModel> characters = movie.getCharacters();
            characters.remove(character);
            movie.setCharacters(characters);
            movieRepository.save(movie);
        } else if (!characterRepository.existsById(characterId)) {
            throw new EntityNotFoundException(ExceptionMsg.CHARACTER_NOT_FOUND);
        } else if (!movieRepository.existsById(movieId)) {
            throw new EntityNotFoundException(ExceptionMsg.MOVIE_NOT_FOUND);
        }
    }

    // Add genre to a movie
    public void addGenreToMovie(Long genreId, Long movieId) {
        if (genreRepository.existsById(genreId) &&
                movieRepository.existsById(movieId)) {
            MovieModel movie = movieRepository.getById(movieId);
            GenreModel genre = genreRepository.getById(genreId);
            Set<GenreModel> genres = movie.getGenres();
            genres.add(genre);
            movie.setGenres(genres);
            movieRepository.save(movie);
        } else if (!genreRepository.existsById(genreId)) {
            throw new EntityNotFoundException(ExceptionMsg.GENRE_NOT_FOUND);
        } else if (!movieRepository.existsById(movieId)) {
            throw new EntityNotFoundException(ExceptionMsg.MOVIE_NOT_FOUND);
        }
    }

    // Remove genre from a movie
    public void removeGenreFromMovie(Long genreId, Long movieId) {
        if (genreRepository.existsById(genreId) &&
                movieRepository.existsById(movieId)) {
            MovieModel movie = movieRepository.getById(movieId);
            GenreModel genre = genreRepository.getById(genreId);
            Set<GenreModel> genres = movie.getGenres();
            genres.remove(genre);
            movie.setGenres(genres);
            movieRepository.save(movie);
        } else if (!genreRepository.existsById(genreId)) {
            throw new EntityNotFoundException(ExceptionMsg.GENRE_NOT_FOUND);
        } else if (!movieRepository.existsById(movieId)) {
            throw new EntityNotFoundException(ExceptionMsg.MOVIE_NOT_FOUND);
        }
    }

    // GetByFilters
    public List<MovieDtoFull> getMoviesByFilters(String name, Set<Long> genres, String order) {
        List<MovieModel> movies = movieRepository.findAll(
                movieSpecification.getByFilters(name, genres, order));
        return movieMapper.toListMovieDtoFull(movies);
    }

}
