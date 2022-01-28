package com.alkemy.disney.validator;

import com.alkemy.disney.dto.*;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DtoValidator {

    public boolean movieDtoIsValid(MovieDto movieDto) {
        boolean valid = false;
        if (movieDto.getImage() != null &&
                movieDto.getTitle() != null &&
                movieDto.getCreationDate() != null &&
                movieDto.getRating() >= 1 &&
                movieDto.getRating() <= 5 &&
                movieDto.getCharacters() != null &&
                movieDto.getGenres() != null) {
            if (!movieDto.getCharacters().isEmpty()) {
                valid = iterateCharacters(movieDto.getCharacters());
            }
            if (!movieDto.getCharacters().isEmpty()) {
                valid = iterateGenres(movieDto.getGenres());
            }
        }
        return valid;
    }

    private boolean iterateCharacters(Set<CharacterDto> charactersDto) {
        for (CharacterDto characterDto : charactersDto) {
            if (!characterDtoIsValid(characterDto)) {
                return false;
            }
        }
        return true;
    }

    public boolean characterDtoIsValid(CharacterDto characterDto) {
        return characterDto.getImage() != null &&
                characterDto.getName() != null &&
                characterDto.getStory() != null &&
                characterDto.getAge() > 0 &&
                characterDto.getWeight() > 0;
    }

    private boolean iterateGenres(Set<GenreDto> genres) {
        for (GenreDto genreDto : genres) {
            if (!genreDtoIsValid(genreDto)) {
                return false;
            }
        }
        return true;
    }

    public boolean genreDtoIsValid(GenreDto genreDto) {
        return genreDto.getImage() != null &&
                genreDto.getName() != null;
    }

    public boolean movieDtoToUpdateIsValid(MovieDto movieDto) {
        return movieDto.getImage() != null &&
                movieDto.getTitle() != null &&
                movieDto.getCreationDate() != null &&
                movieDto.getRating() >= 1 &&
                movieDto.getRating() <= 5;
    }

}
