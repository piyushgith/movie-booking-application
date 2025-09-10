package com.micro.piyush.movie.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTicketResponse {
    private Integer ticketId;
    private Integer totalAmount;
    private String message;
}