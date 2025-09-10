package com.micro.piyush.movie.repository;


import com.micro.piyush.movie.entity.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Integer> {
    List<ShowSeat> findByIdInAndIsAvailableTrue(List<Integer> seatIds);

    @Query("SELECT ss FROM ShowSeat ss WHERE ss.show.id = :showId ORDER BY ss.seatNo")
    List<ShowSeat> findByShowIdOrderBySeatNo(@Param("showId") Integer showId);

    // Alternative with show entity
    @Query("SELECT ss FROM ShowSeat ss JOIN FETCH ss.show WHERE ss.show.id = :showId ORDER BY ss.seatNo")
    List<ShowSeat> findByShowIdWithShowDetails(@Param("showId") Integer showId);

    // Native query version (if needed)
    @Query(value = "SELECT * FROM SHOW_SEAT WHERE show_id = :showId ORDER BY seat_no", nativeQuery = true)
    List<ShowSeat> findByShowIdNative(@Param("showId") Integer showId);

}
