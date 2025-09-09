package com.micro.piyush.movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "ticket_seat")
public class TicketSeat {

    @EmbeddedId
    private TicketSeatId id;

//    @Column(name = "show_seat_id")
//    private Integer showSeatId;

    @MapsId("ticketId") // Maps ticketId to the ticket relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    @JsonIgnore
    private Ticket ticket;

    @MapsId("showSeatId") // Maps showSeatId to the showSeat relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_seat_id", nullable = false)
    @JsonIgnore
    private ShowSeat showSeat;

}

