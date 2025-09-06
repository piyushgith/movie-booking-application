package com.micro.piyush.movie.entity;

import jakarta.persistence.*;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "ticket_seat")
@IdClass(TicketSeatId.class)
public class TicketSeat {

    @Id
    @Column(name = "ticket_id")
    private Integer ticketId;

    @Id
    @Column(name = "show_seat_id")
    private Integer showSeatId;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", insertable = false, updatable = false)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_seat_id", insertable = false, updatable = false)
    private ShowSeat showSeat;

}

