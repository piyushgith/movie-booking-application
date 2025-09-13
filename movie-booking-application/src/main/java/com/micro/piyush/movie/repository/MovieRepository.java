package com.micro.piyush.movie.repository;

import com.micro.piyush.movie.entity.Movie;
import com.micro.piyush.movie.enums.GenreType;
import com.micro.piyush.movie.enums.LanguageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>, JpaSpecificationExecutor<Movie> {

    Movie findByMovieName(String name);

    // Find by movie name (case insensitive)
    Optional<Movie> findByMovieNameIgnoreCase(String movieName);

    // Find by movie name with partial match
    @Query("SELECT m FROM Movie m WHERE LOWER(m.movieName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Movie> findByMovieNameContaining(@Param("name") String name);

    // Find by genre
    List<Movie> findByGenre(GenreType genre);

    // Find by language
    List<Movie> findByLanguage(LanguageType language);

    // Find by rating range
    List<Movie> findByRatingBetween(Float minRating, Float maxRating);

    // Find by release date range
    List<Movie> findByReleaseDateBetween(LocalDate startDate, LocalDate endDate);

    // Check if movie name exists (for uniqueness validation)
    boolean existsByMovieNameIgnoreCaseAndIdNot(String movieName, Integer id);

    // Find top rated movies
    @Query("SELECT m FROM Movie m WHERE m.rating IS NOT NULL ORDER BY m.rating DESC")
    Page<Movie> findTopRatedMovies(Pageable pageable);

    // Find recently released movies
    @Query("SELECT m FROM Movie m WHERE m.releaseDate <= CURRENT_DATE ORDER BY m.releaseDate DESC")
    Page<Movie> findRecentlyReleasedMovies(Pageable pageable);

    // Find upcoming movies
    @Query("SELECT m FROM Movie m WHERE m.releaseDate > CURRENT_DATE ORDER BY m.releaseDate ASC")
    Page<Movie> findUpcomingMovies(Pageable pageable);

    // Search movies with multiple criteria
    @Query("SELECT m FROM Movie m WHERE " +
            "(:name IS NULL OR LOWER(m.movieName) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:genre IS NULL OR m.genre = :genre) " +
            "AND (:language IS NULL OR m.language = :language) " +
            "AND (:minRating IS NULL OR m.rating >= :minRating) " +
            "AND (:maxRating IS NULL OR m.rating <= :maxRating) " +
            "AND (:startDate IS NULL OR m.releaseDate >= :startDate) " +
            "AND (:endDate IS NULL OR m.releaseDate <= :endDate) " +
            "ORDER BY m.movieName")
    Page<Movie> searchMovies(
            @Param("name") String name,
            @Param("genre") GenreType genre,
            @Param("language") LanguageType language,
            @Param("minRating") Float minRating,
            @Param("maxRating") Float maxRating,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);
}