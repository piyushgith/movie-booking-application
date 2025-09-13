package com.micro.piyush.movie.request;


import com.micro.piyush.movie.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto {
    private Integer id;
    private Integer duration;
    private String genre;
    private String language;
    private String movieName;
    private String movieImage;
    private Float rating;
    private LocalDate releaseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MovieDto(Movie movie) {
        this.id = movie.getId();
        this.duration = movie.getDuration();
        this.genre = movie.getGenre() != null ? movie.getGenre().name() : null;
        this.language = movie.getLanguage() != null ? movie.getLanguage().name() : null;
        this.movieName = movie.getMovieName();
        this.movieImage = movie.getMovieImage();
        this.rating = movie.getRating();
        this.releaseDate = movie.getReleaseDate();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
