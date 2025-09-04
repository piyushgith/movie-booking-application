package com.micro.piyush.movie.request;

import com.micro.piyush.movie.enums.GenreType;
import com.micro.piyush.movie.enums.LanguageType;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;


@Data
public class MovieRequest {
    private String movieName;
    private Integer duration;
    private Float rating;
    private LocalDate releaseDate;
    private GenreType genreType;
    private LanguageType languageType;
}