package com.micro.piyush.movie.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTicketRequest {
    private Integer userId;
    private Integer showId;
    private List<Integer> seatIds;
}