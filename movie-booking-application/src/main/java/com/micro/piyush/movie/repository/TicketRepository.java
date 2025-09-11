package com.micro.piyush.movie.repository;

import com.micro.piyush.movie.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    // Find all tickets for a user with all necessary data fetched
    @Query("SELECT DISTINCT t FROM Ticket t " +
            "JOIN FETCH t.show s " +
            "JOIN FETCH s.movie m " +
            "JOIN FETCH s.theater th " +
            "JOIN FETCH t.ticketSeats ts " +
            "JOIN FETCH ts.showSeat ss " +
            "WHERE t.user.id = :userId " +
            "ORDER BY t.bookedAt DESC")
    List<Ticket> findTicketsByUserId(@Param("userId") Integer userId);

    // Find tickets with pagination
    @Query("SELECT DISTINCT t FROM Ticket t " +
            "JOIN FETCH t.show s " +
            "JOIN FETCH s.movie m " +
            "JOIN FETCH s.theater th " +
            "JOIN FETCH t.ticketSeats ts " +
            "JOIN FETCH ts.showSeat ss " +
            "WHERE t.user.id = :userId " +
            "ORDER BY t.bookedAt DESC")
    Page<Ticket> findTicketsByUserId(@Param("userId") Integer userId, Pageable pageable);

    // Count tickets for a user
    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.user.id = :userId")
    long countTicketsByUserId(@Param("userId") Integer userId);

    // Find ticket by ID with all details for single ticket view
    @Query("SELECT t FROM Ticket t " +
            "JOIN FETCH t.show s " +
            "JOIN FETCH s.movie m " +
            "JOIN FETCH s.theater th " +
            "JOIN FETCH t.ticketSeats ts " +
            "JOIN FETCH ts.showSeat ss " +
            "WHERE t.id = :ticketId AND t.user.id = :userId")
    Optional<Ticket> findTicketByIdAndUserId(@Param("ticketId") Integer ticketId, @Param("userId") Integer userId);
}
