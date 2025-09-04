package com.micro.piyush.movie.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ShowRequest {

    private LocalTime showStartTime;
    private LocalDate showDate;
    private Integer theaterId;
    private Integer movieId;
}
