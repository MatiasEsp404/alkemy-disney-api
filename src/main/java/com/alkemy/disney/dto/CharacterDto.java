package com.alkemy.disney.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterDto {
    private Long id;
    private String image;
    private String name;
    private Short age;
    private Float weight;
    private String story;
}
