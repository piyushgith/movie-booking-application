package com.micro.piyush.movie.request;


import com.micro.piyush.movie.enums.GenreType;
import com.micro.piyush.movie.enums.LanguageType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieRequest {
    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be greater than 0")
    private Integer duration;

    @NotNull(message = "Genre is required")
    private GenreType genre;

    @NotNull(message = "Language is required")
    private LanguageType language;

    @NotBlank(message = "Movie name is required")
    private String movieName;

    private String movieImage;

    @DecimalMin(value = "0.0", message = "Rating must be between 0 and 10")
    @DecimalMax(value = "10.0", message = "Rating must be between 0 and 10")
    private Float rating;

    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;
}
