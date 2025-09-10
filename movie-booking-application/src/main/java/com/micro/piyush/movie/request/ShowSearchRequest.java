package com.micro.piyush.movie.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowSearchRequest {
    private Integer movieId;
    private Integer theaterId;
    private LocalDate date;
    private String movieName;
    private String theaterName;
    private String location;
}
