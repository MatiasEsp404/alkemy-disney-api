package com.alkemy.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CharacterDtoFull {
    private Long id;
    private String image;
    private String name;
    private Short age;
    private Float weight;
    private String story;
    private Set<MovieDto> movies;
}
