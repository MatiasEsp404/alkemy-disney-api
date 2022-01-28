package com.alkemy.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;

@Getter
@Setter
public class MovieDto {
    private Long id;
    private String image;
    private String title;
    private Date creationDate;
    private Float rating;
    private Set<CharacterDto> characters;
    private Set<GenreDto> genres;
}
