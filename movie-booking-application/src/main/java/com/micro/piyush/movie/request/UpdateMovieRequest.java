package com.micro.piyush.movie.request;

import com.micro.piyush.movie.enums.GenreType;
import com.micro.piyush.movie.enums.LanguageType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMovieRequest {
    @Min(value = 1, message = "Duration must be greater than 0")
    private Integer duration;

    private GenreType genre;

    private LanguageType language;

    @NotBlank(message = "Movie name is required")
    private String movieName;

    private String movieImage;

    @DecimalMin(value = "0.0", message = "Rating must be between 0 and 10")
    @DecimalMax(value = "10.0", message = "Rating must be between 0 and 10")
    private Float rating;

    private LocalDate releaseDate;
}
