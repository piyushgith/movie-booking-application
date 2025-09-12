package com.micro.piyush.movie.request;


import com.micro.piyush.movie.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTicketDto {
    private Integer ticketId;
    private String movieName;
    private LocalDate showDate;
    private LocalTime showTime;
    private String seats; // Comma-separated seat numbers
    private LocalDateTime bookingDate;
    private Integer totalPrice;
    private String theaterName;

    public UserTicketDto(Ticket ticket) {
        this.ticketId = ticket.getTicketId();
        this.movieName = ticket.getShow() != null && ticket.getShow().getMovie() != null
                ? ticket.getShow().getMovie().getMovieName() : null;
        this.showDate = ticket.getShow() != null ? ticket.getShow().getDate() : null;
        this.showTime = ticket.getShow() != null ? ticket.getShow().getTime() : null;
        this.bookingDate = ticket.getBookedAt();
        this.totalPrice = ticket.getTotalTicketsPrice();
        this.theaterName = ticket.getShow() != null && ticket.getShow().getTheater() != null
                ? ticket.getShow().getTheater().getName() : null;

        // Extract seat numbers from ticket seats
        if (ticket.getTicketSeats() != null && !ticket.getTicketSeats().isEmpty()) {
            this.seats = ticket.getTicketSeats().stream()
                    .map(ts -> ts.getShowSeat().getSeatNo())
                    .sorted()
                    .collect(Collectors.joining(", "));
        } else {
            this.seats = "";
        }
    }
}
