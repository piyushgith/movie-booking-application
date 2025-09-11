package com.micro.piyush.movie.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookTicketResponse {
    private Integer bookingId;
    private Integer totalAmount;
    private String movieName;
    private String showTime;
    private String message;
    private List<String> bookedSeats;
}
