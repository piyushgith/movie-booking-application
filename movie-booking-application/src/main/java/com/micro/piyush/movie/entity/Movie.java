package com.micro.piyush.movie.entity;

import com.micro.piyush.movie.enums.GenreType;
import com.micro.piyush.movie.enums.LanguageType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String movieName;

    @Column(nullable = false)
    private Integer duration;

    @Enumerated(EnumType.STRING)
    private GenreType genre;

    @Enumerated(EnumType.STRING)
    private LanguageType language;

    @Column(nullable = false)
    private Float rating;

    private LocalDate releaseDate;

}
