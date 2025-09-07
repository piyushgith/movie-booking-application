package com.micro.piyush.movie.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.micro.piyush.movie.enums.GenreType;
import com.micro.piyush.movie.enums.LanguageType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "duration", nullable = false)
    @Min(value = 1, message = "Duration must be greater than 0")
    private Integer duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private GenreType genre;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private LanguageType language;

    @Column(name = "movie_name", nullable = false, unique = true)
    @NotBlank(message = "Movie name is required")
    private String movieName;

    @Column(name = "movie_image")
    private String movieImage;

    @Column(name = "rating")
    @DecimalMin(value = "0.0", message = "Rating must be between 0 and 10")
    @DecimalMax(value = "10.0", message = "Rating must be between 0 and 10")
    private Float rating;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    // Relationships
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference // This side is serialized in a bidirectional relationship
    private List<Show> shows;

}
