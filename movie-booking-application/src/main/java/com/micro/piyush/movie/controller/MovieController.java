package com.micro.piyush.movie.controller;


import com.micro.piyush.movie.entity.Movie;
import com.micro.piyush.movie.enums.GenreType;
import com.micro.piyush.movie.enums.LanguageType;
import com.micro.piyush.movie.request.CreateMovieRequest;
import com.micro.piyush.movie.request.MovieDto;
import com.micro.piyush.movie.request.MovieRequest;
import com.micro.piyush.movie.request.UpdateMovieRequest;
import com.micro.piyush.movie.response.MovieResponse;
import com.micro.piyush.movie.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // Create a new movie
    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@Valid @RequestBody CreateMovieRequest request) {
        MovieDto movie = movieService.createMovie(request);
        return new ResponseEntity<>(movie, HttpStatus.CREATED);
    }

    // Get movie by ID
    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Integer id) {
        MovieDto movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movie);
    }

    // Get all movies with pagination
    @GetMapping
    public ResponseEntity<MovieResponse> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "movieName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        MovieResponse response = movieService.getAllMovies(pageable);
        return ResponseEntity.ok(response);
    }

    // Update movie
    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateMovieRequest request) {
        MovieDto movie = movieService.updateMovie(id, request);
        return ResponseEntity.ok(movie);
    }

    // Delete movie
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    // Search movies
    @GetMapping("/search")
    public ResponseEntity<MovieResponse> searchMovies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) GenreType genre,
            @RequestParam(required = false) LanguageType language,
            @RequestParam(required = false) Float minRating,
            @RequestParam(required = false) Float maxRating,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("movieName").ascending());
        MovieResponse response = movieService.searchMovies(name, genre, language, minRating, maxRating, startDate, endDate, pageable);
        return ResponseEntity.ok(response);
    }

    // Get movies by genre
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<MovieDto>> getMoviesByGenre(@PathVariable GenreType genre) {
        List<MovieDto> movies = movieService.getMoviesByGenre(genre);
        return ResponseEntity.ok(movies);
    }

    // Get movies by language
    @GetMapping("/language/{language}")
    public ResponseEntity<List<MovieDto>> getMoviesByLanguage(@PathVariable LanguageType language) {
        List<MovieDto> movies = movieService.getMoviesByLanguage(language);
        return ResponseEntity.ok(movies);
    }

    // Get movies by rating range
    @GetMapping("/rating")
    public ResponseEntity<List<MovieDto>> getMoviesByRatingRange(
            @RequestParam Float minRating,
            @RequestParam Float maxRating) {
        List<MovieDto> movies = movieService.getMoviesByRatingRange(minRating, maxRating);
        return ResponseEntity.ok(movies);
    }

    // Get top rated movies
    @GetMapping("/top-rated")
    public ResponseEntity<MovieResponse> getTopRatedMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());
        MovieResponse response = movieService.getTopRatedMovies(pageable);
        return ResponseEntity.ok(response);
    }

    // Get recently released movies
    @GetMapping("/recent")
    public ResponseEntity<MovieResponse> getRecentlyReleasedMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("releaseDate").descending());
        MovieResponse response = movieService.getRecentlyReleasedMovies(pageable);
        return ResponseEntity.ok(response);
    }

    // Get upcoming movies
    @GetMapping("/upcoming")
    public ResponseEntity<MovieResponse> getUpcomingMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("releaseDate").ascending());
        MovieResponse response = movieService.getUpcomingMovies(pageable);
        return ResponseEntity.ok(response);
    }

    // Get all movies (no pagination)
    @GetMapping("/all")
    public ResponseEntity<List<MovieDto>> getAllMoviesNoPagination() {
        List<MovieDto> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }
}
