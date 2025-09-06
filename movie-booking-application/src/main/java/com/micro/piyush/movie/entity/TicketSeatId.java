package com.micro.piyush.movie.entity;

import java.io.Serializable;
import java.util.Objects;

public class TicketSeatId implements Serializable {

    private Integer ticketId;
    private Integer showSeatId;

    public TicketSeatId() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketSeatId that = (TicketSeatId) o;
        return Objects.equals(ticketId, that.ticketId) &&
                Objects.equals(showSeatId, that.showSeatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, showSeatId);
    }
}