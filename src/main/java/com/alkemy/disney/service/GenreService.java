package com.alkemy.disney.service;

import com.alkemy.disney.dto.GenreDto;
import com.alkemy.disney.dto.GenreDtoMinified;
import com.alkemy.disney.dto.GenreDtoFull;
import com.alkemy.disney.exception.GenreException;
import com.alkemy.disney.exception.msg.ExceptionMsg;
import com.alkemy.disney.mapper.GenreMapper;
import com.alkemy.disney.model.GenreModel;
import com.alkemy.disney.model.MovieModel;
import com.alkemy.disney.repository.GenreRepository;
import com.alkemy.disney.validator.DtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GenreMapper genreMapper;

    @Autowired
    private DtoValidator dtoValidator;

    @Autowired
    private MovieService movieService;

    public GenreDto saveGenre(GenreDto genreDto) {
        if (dtoValidator.genreDtoIsValid(genreDto)) {
            GenreModel genre = genreMapper.toGenreModel(genreDto);
            genre = genreRepository.save(genre);
            return genreMapper.toGenreDto(genre);
        } else {
            throw new GenreException(ExceptionMsg.DTO_WRONG_DATA);
        }
    }

    public GenreDto updateGenre(GenreDto genreDto) {
        if (genreRepository.existsById(genreDto.getId())){
            if (dtoValidator.genreDtoIsValid(genreDto)) {
                GenreModel genre = genreMapper.toGenreModel(genreDto);
                genre = genreRepository.save(genre);
                return genreMapper.toGenreDto(genre);
            } else {
                throw new GenreException(ExceptionMsg.DTO_WRONG_DATA);
            }
        } else {
            throw new GenreException(ExceptionMsg.GENRE_NOT_FOUND);
        }
    }

    public void deleteGenre(Long id) {
        if (genreRepository.existsById(id)) {
            unGenre(id); // Method name inspired by orwell's 1984.
            genreRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(ExceptionMsg.GENRE_NOT_FOUND);
        }
    }

    private void unGenre(Long id){
        // In 1984, George Orwell created the concept of the unperson, someone who had been executed and of whose existence all records were erased.
        GenreModel genre = genreRepository.getById(id);
        if (!genre.getMovies().isEmpty()) {
            Set<MovieModel> movies = genre.getMovies();
            for (MovieModel movie : movies) {
                movieService.removeGenreFromMovie(genre.getId(), movie.getId());
            }
        }
    }

    public GenreDtoFull getGenre(Long id) {
        if (genreRepository.existsById(id)) {
            GenreModel genre = genreRepository.getById(id);
            return genreMapper.toGenreDtoFull(genre);
        } else {
            throw new EntityNotFoundException(ExceptionMsg.GENRE_NOT_FOUND);
        }
    }

    public List<GenreDtoMinified> getAllGenres() {
        List<GenreModel> genres = genreRepository.findAll();
        return genreMapper.toListGenreDtoMinified(genres);
    }
}
