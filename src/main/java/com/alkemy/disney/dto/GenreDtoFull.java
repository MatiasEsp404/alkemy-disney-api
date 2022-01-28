package com.alkemy.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class GenreDtoFull {
    private Long id;
    private String name;
    private String image;
    private Set<MovieDto> movies;
}
