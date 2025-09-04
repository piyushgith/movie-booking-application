package com.micro.piyush.movie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket_seats")
public class TicketSeat {

    @EmbeddedId
    private TicketSeatId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ticketId")
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("showSeatId")
    @JoinColumn(name = "show_seat_id")
    private ShowSeat showSeat;

}

