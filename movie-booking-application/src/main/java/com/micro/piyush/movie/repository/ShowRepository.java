package com.micro.piyush.movie.repository;

import com.micro.piyush.movie.entity.Show;
import com.micro.piyush.movie.enums.LocationType;
import com.micro.piyush.movie.request.ShowDto;
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
public interface ShowRepository extends JpaRepository<Show, Integer>, JpaSpecificationExecutor<Show> {

    @Query("SELECT s FROM Show s LEFT JOIN FETCH s.movie LEFT JOIN FETCH s.theater")
    List<Show> findAllShowsWithDetails();

    @Query("SELECT s FROM Show s LEFT JOIN FETCH s.movie LEFT JOIN FETCH s.theater WHERE s.showId = :id")
    Show findShowWithDetails(@Param("id") Integer id);

    // JPQL query to fetch shows for a given location, along with related movie and theater details
    @Query("SELECT s FROM Show s JOIN FETCH s.movie m JOIN FETCH s.theater t WHERE t.location = :location")
    List<Show> findShowsByTheaterLocation(@Param("location") LocationType location);

    // Or using the standard Spring Data JPA query method naming convention
    List<Show> findByMovieId(int movieId);

    @Query("SELECT s FROM Show s JOIN FETCH s.movie JOIN FETCH s.theater WHERE s.id = :showId")
    Optional<Show> findByIdWithMovieAndTheater(@Param("showId") Integer showId);

    // Basic queries
    @Query("SELECT s FROM Show s JOIN FETCH s.movie JOIN FETCH s.theater WHERE s.date >= CURRENT_DATE ORDER BY s.date, s.time")
    List<Show> findUpcomingShows();

    @Query("SELECT s FROM Show s JOIN FETCH s.movie JOIN FETCH s.theater WHERE s.movie.id = :movieId ORDER BY s.date, s.time")
    List<Show> findByMovieId(@Param("movieId") Integer movieId);

    @Query("SELECT s FROM Show s JOIN FETCH s.movie JOIN FETCH s.theater WHERE s.theater.id = :theaterId ORDER BY s.date, s.time")
    List<Show> findByTheaterId(@Param("theaterId") Integer theaterId);

    @Query("SELECT s FROM Show s JOIN FETCH s.movie JOIN FETCH s.theater WHERE s.date = :date ORDER BY s.time")
    List<Show> findByDate(@Param("date") LocalDate date);

    // Complex search with multiple criteria
    @Query("SELECT s FROM Show s JOIN FETCH s.movie m JOIN FETCH s.theater t " +
            "WHERE (:movieId IS NULL OR m.id = :movieId) " +
            "AND (:theaterId IS NULL OR t.id = :theaterId) " +
            "AND (:date IS NULL OR s.date = :date) " +
            "AND (:movieName IS NULL OR LOWER(m.movieName) LIKE LOWER(CONCAT('%', :movieName, '%'))) " +
            "AND (:theaterName IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :theaterName, '%'))) " +
            "AND (:location IS NULL OR LOWER(t.location) LIKE LOWER(CONCAT('%', :location, '%'))) " +
            "AND s.date >= CURRENT_DATE " +
            "ORDER BY s.date, s.time")
    Page<Show> searchShows(
            @Param("movieId") Integer movieId,
            @Param("theaterId") Integer theaterId,
            @Param("date") LocalDate date,
            @Param("movieName") String movieName,
            @Param("theaterName") String theaterName,
            @Param("location") String location,
            Pageable pageable);
}