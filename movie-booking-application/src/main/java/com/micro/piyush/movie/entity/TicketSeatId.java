package com.micro.piyush.movie.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketSeatId implements Serializable {

    private Integer ticketId;
    private Integer showSeatId;

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