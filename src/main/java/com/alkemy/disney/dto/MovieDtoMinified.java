package com.alkemy.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class MovieDtoMinified {
    private Long id;
    private String image;
    private String title;
    private Date creationDate;
}
