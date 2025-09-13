package com.micro.piyush.movie.service;

import com.micro.piyush.movie.entity.Movie;
import com.micro.piyush.movie.enums.GenreType;
import com.micro.piyush.movie.enums.LanguageType;
import com.micro.piyush.movie.exception.MovieAlreadyExist;
import com.micro.piyush.movie.exception.MovieDoesNotExists;
import com.micro.piyush.movie.mapper.MovieMapper;
import com.micro.piyush.movie.repository.MovieRepository;
import com.micro.piyush.movie.request.CreateMovieRequest;
import com.micro.piyush.movie.request.MovieDto;
import com.micro.piyush.movie.request.MovieRequest;
import com.micro.piyush.movie.request.UpdateMovieRequest;
import com.micro.piyush.movie.response.MovieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    // Create a new movie
    public MovieDto createMovie(CreateMovieRequest request) {
        // Check if movie name already exists
        if (movieRepository.findByMovieNameIgnoreCase(request.getMovieName()).isPresent()) {
            throw new RuntimeException("Movie with name '" + request.getMovieName() + "' already exists");
        }

        Movie movie = Movie.builder()
                .duration(request.getDuration())
                .genre(request.getGenre())
                .language(request.getLanguage())
                .movieName(request.getMovieName())
                .movieImage(request.getMovieImage())
                .rating(request.getRating())
                .releaseDate(request.getReleaseDate())
                .build();

        Movie savedMovie = movieRepository.save(movie);
        return new MovieDto(savedMovie);
    }

    // Get movie by ID
    @Transactional(readOnly = true)
    public MovieDto getMovieById(Integer id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + id));
        return new MovieDto(movie);
    }

    // Get all movies with pagination
    @Transactional(readOnly = true)
    public MovieResponse getAllMovies(Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        List<MovieDto> movieDtos = moviePage.getContent().stream()
                .map(MovieDto::new)
                .collect(Collectors.toList());

        return new MovieResponse(
                movieDtos,
                moviePage.getTotalElements(),
                moviePage.getTotalPages(),
                moviePage.getNumber(),
                moviePage.getSize()
        );
    }

    // Update movie
    public MovieDto updateMovie(Integer id, UpdateMovieRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + id));

        // Check if movie name already exists (excluding current movie)
        if (request.getMovieName() != null &&
                !request.getMovieName().equals(movie.getMovieName()) &&
                movieRepository.existsByMovieNameIgnoreCaseAndIdNot(request.getMovieName(), id)) {
            throw new RuntimeException("Movie with name '" + request.getMovieName() + "' already exists");
        }

        // Update fields if provided
        if (request.getDuration() != null) movie.setDuration(request.getDuration());
        if (request.getGenre() != null) movie.setGenre(request.getGenre());
        if (request.getLanguage() != null) movie.setLanguage(request.getLanguage());
        if (request.getMovieName() != null) movie.setMovieName(request.getMovieName());
        if (request.getMovieImage() != null) movie.setMovieImage(request.getMovieImage());
        if (request.getRating() != null) movie.setRating(request.getRating());
        if (request.getReleaseDate() != null) movie.setReleaseDate(request.getReleaseDate());

        Movie updatedMovie = movieRepository.save(movie);
        return new MovieDto(updatedMovie);
    }

    // Delete movie
    public void deleteMovie(Integer id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + id));

        // Check if movie has shows (business logic - prevent deletion if shows exist)
        if (movie.getShows() != null && !movie.getShows().isEmpty()) {
            throw new RuntimeException("Cannot delete movie with existing shows. Please delete shows first.");
        }

        movieRepository.delete(movie);
    }

    // Search movies
    @Transactional(readOnly = true)
    public MovieResponse searchMovies(String name, GenreType genre, LanguageType language,
                                      Float minRating, Float maxRating,
                                      LocalDate startDate, LocalDate endDate,
                                      Pageable pageable) {
        Page<Movie> moviePage = movieRepository.searchMovies(
                name, genre, language, minRating, maxRating, startDate, endDate, pageable);

        List<MovieDto> movieDtos = moviePage.getContent().stream()
                .map(MovieDto::new)
                .collect(Collectors.toList());

        return new MovieResponse(
                movieDtos,
                moviePage.getTotalElements(),
                moviePage.getTotalPages(),
                moviePage.getNumber(),
                moviePage.getSize()
        );
    }

    // Get movies by genre
    @Transactional(readOnly = true)
    public List<MovieDto> getMoviesByGenre(GenreType genre) {
        List<Movie> movies = movieRepository.findByGenre(genre);
        return movies.stream()
                .map(MovieDto::new)
                .collect(Collectors.toList());
    }

    // Get movies by language
    @Transactional(readOnly = true)
    public List<MovieDto> getMoviesByLanguage(LanguageType language) {
        List<Movie> movies = movieRepository.findByLanguage(language);
        return movies.stream()
                .map(MovieDto::new)
                .collect(Collectors.toList());
    }

    // Get movies by rating range
    @Transactional(readOnly = true)
    public List<MovieDto> getMoviesByRatingRange(Float minRating, Float maxRating) {
        List<Movie> movies = movieRepository.findByRatingBetween(minRating, maxRating);
        return movies.stream()
                .map(MovieDto::new)
                .collect(Collectors.toList());
    }

    // Get top rated movies
    @Transactional(readOnly = true)
    public MovieResponse getTopRatedMovies(Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findTopRatedMovies(pageable);
        List<MovieDto> movieDtos = moviePage.getContent().stream()
                .map(MovieDto::new)
                .collect(Collectors.toList());

        return new MovieResponse(
                movieDtos,
                moviePage.getTotalElements(),
                moviePage.getTotalPages(),
                moviePage.getNumber(),
                moviePage.getSize()
        );
    }

    // Get recently released movies
    @Transactional(readOnly = true)
    public MovieResponse getRecentlyReleasedMovies(Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findRecentlyReleasedMovies(pageable);
        List<MovieDto> movieDtos = moviePage.getContent().stream()
                .map(MovieDto::new)
                .collect(Collectors.toList());

        return new MovieResponse(
                movieDtos,
                moviePage.getTotalElements(),
                moviePage.getTotalPages(),
                moviePage.getNumber(),
                moviePage.getSize()
        );
    }

    // Get upcoming movies
    @Transactional(readOnly = true)
    public MovieResponse getUpcomingMovies(Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findUpcomingMovies(pageable);
        List<MovieDto> movieDtos = moviePage.getContent().stream()
                .map(MovieDto::new)
                .collect(Collectors.toList());

        return new MovieResponse(
                movieDtos,
                moviePage.getTotalElements(),
                moviePage.getTotalPages(),
                moviePage.getNumber(),
                moviePage.getSize()
        );
    }

    // Get all movies (no pagination)
    @Transactional(readOnly = true)
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(MovieDto::new)
                .collect(Collectors.toList());
    }

}