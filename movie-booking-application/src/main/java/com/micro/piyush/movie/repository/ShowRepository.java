package com.micro.piyush.movie.repository;

import com.micro.piyush.movie.entity.Show;
import com.micro.piyush.movie.enums.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {

    // JPQL query to fetch shows for a given location, along with related movie and theater details
    @Query("SELECT s FROM Show s JOIN FETCH s.movie m JOIN FETCH s.theater t WHERE t.location = :location")
    List<Show> findShowsByTheaterLocation(@Param("location") LocationType location);

    // Or using the standard Spring Data JPA query method naming convention
    List<Show> findByMovieId(int movieId);

//    @Query(value = "select time from shows where date = :date and movie_id = :movieId and theater_id = :theaterId" , nativeQuery = true)
//    public List<Time> getShowTimingsOnDate(@Param("date")Date date, @Param("theaterId")Integer theaterId, @Param("movieId")Integer movieId);
//
//    @Query(value = "select movie_id from shows group by movie_id order by count(*) desc limit 1" , nativeQuery = true)
//    public Integer getMostShowsMovie();
//
//    @Query(value = "select * from shows where movie_id = :movieId" , nativeQuery = true)
//    public List<Show> getAllShowsOfMovie(@Param("movieId")Integer movieId);
}
