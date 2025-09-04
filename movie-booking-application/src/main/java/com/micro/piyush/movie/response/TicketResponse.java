package com.micro.piyush.movie.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TicketResponse {
    private LocalTime time;
    private LocalDate date;
    private String movieName;
    private String theaterName;
    private String address;
    private String bookedSeats;
    private Integer totalPrice;
}