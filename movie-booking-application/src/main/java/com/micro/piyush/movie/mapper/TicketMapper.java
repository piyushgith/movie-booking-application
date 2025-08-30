package com.micro.piyush.movie.mapper;

import com.micro.piyush.movie.entity.Show;
import com.micro.piyush.movie.entity.Ticket;
import com.micro.piyush.movie.response.TicketResponse;

public class TicketMapper {
    public static TicketResponse returnTicket(Show show, Ticket ticket) {
        TicketResponse ticketResponseDto = TicketResponse.builder()
                .bookedSeats(ticket.getBookedSeats())
                .address(show.getTheater().getAddress())
                .theaterName(show.getTheater().getName())
                .movieName(show.getMovie().getMovieName())
                .date(show.getDate())
                .time(show.getTime())
                .totalPrice(ticket.getTotalTicketsPrice())
                .build();

        return ticketResponseDto;
    }
}
