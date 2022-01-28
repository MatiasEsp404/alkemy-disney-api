package com.alkemy.disney.service;

import com.alkemy.disney.dto.CharacterDto;
import com.alkemy.disney.dto.CharacterDtoMinified;
import com.alkemy.disney.dto.CharacterDtoFull;
import com.alkemy.disney.exception.CharacterException;
import com.alkemy.disney.exception.msg.ExceptionMsg;
import com.alkemy.disney.mapper.CharacterMapper;
import com.alkemy.disney.model.CharacterModel;
import com.alkemy.disney.model.MovieModel;
import com.alkemy.disney.repository.CharacterRepository;
import com.alkemy.disney.repository.specifications.CharacterSpecification;
import com.alkemy.disney.validator.DtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private CharacterMapper characterMapper;

    @Autowired
    private DtoValidator dtoValidator;

    @Autowired
    private CharacterSpecification characterSpecification;

    @Autowired
    private MovieService movieService;

    public List<CharacterDtoMinified> getAllCharacters() {
        List<CharacterModel> characters = characterRepository.findAll();
        return characterMapper.toListCharacterDtoMinified(characters);
    }

    @Transactional
    public CharacterDto saveCharacter(CharacterDto characterDto) {
        if (dtoValidator.characterDtoIsValid(characterDto)) {
            CharacterModel character = characterMapper.toCharacterModel(characterDto);
            character = characterRepository.save(character);
            return characterMapper.toCharacterDto(character);
        } else {
            throw new CharacterException(ExceptionMsg.DTO_WRONG_DATA);
        }
    }

    @Transactional
    public CharacterDto updateCharacter(CharacterDto characterDto) {
        if (characterRepository.existsById(characterDto.getId())){
            if (dtoValidator.characterDtoIsValid(characterDto)) {
                CharacterModel character = characterMapper.toCharacterModel(characterDto);
                character = characterRepository.save(character);
                return characterMapper.toCharacterDto(character);
            } else {
                throw new CharacterException(ExceptionMsg.DTO_WRONG_DATA);
            }
        } else {
            throw new CharacterException(ExceptionMsg.CHARACTER_NOT_FOUND);
        }
    }

    public void deleteCharacter(Long id) {
        if (characterRepository.existsById(id)) {
            unCharacter(id); // Method name inspired by orwell's 1984.
            characterRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(ExceptionMsg.CHARACTER_NOT_FOUND);
        }
    }

    private void unCharacter(Long id){
        // In 1984, George Orwell created the concept of the unperson, someone who had been executed and of whose existence all records were erased.
        CharacterModel character = characterRepository.getById(id);
        if (!character.getMovies().isEmpty()) {
            Set<MovieModel> movies = character.getMovies();
            for (MovieModel movie : movies) {
                movieService.removeCharacterFromMovie(character.getId(), movie.getId());
            }
        }
    }

    public CharacterDtoFull getCharacter(Long id) {
        if (characterRepository.existsById(id)) {
            CharacterModel character = characterRepository.getById(id);
            return characterMapper.toCharacterDtoFull(character);
        } else {
            throw new EntityNotFoundException(ExceptionMsg.CHARACTER_NOT_FOUND);
        }
    }

    public List<CharacterDtoFull> getCharactersByFilters(String name, Short age, Set<Long> movies) {
        List<CharacterModel>characters = characterRepository.findAll(
                characterSpecification.getByFilters(name, age, movies));
        return characterMapper.toListCharacterDto(characters);
    }
}
