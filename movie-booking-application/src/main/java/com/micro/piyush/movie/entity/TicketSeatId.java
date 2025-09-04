package com.micro.piyush.movie.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TicketSeatId implements java.io.Serializable {

    @Column(name = "ticket_id")
    private Integer ticketId;

    @Column(name = "show_seat_id")
    private Integer showSeatId;

    // Default constructor
    public TicketSeatId() {}

    public TicketSeatId(Integer ticketId, Integer showSeatId) {
        this.ticketId = ticketId;
        this.showSeatId = showSeatId;
    }

    // Getters and Setters
    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getShowSeatId() {
        return showSeatId;
    }

    public void setShowSeatId(Integer showSeatId) {
        this.showSeatId = showSeatId;
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketSeatId that = (TicketSeatId) o;
        return ticketId.equals(that.ticketId) && showSeatId.equals(that.showSeatId);
    }

    @Override
    public int hashCode() {
        return 31 * ticketId.hashCode() + showSeatId.hashCode();
    }
}
