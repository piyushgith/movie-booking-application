package com.micro.piyush.movie.response;

import com.micro.piyush.movie.entity.Show;
import com.micro.piyush.movie.request.ShowSeatDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeatResponse {
    private Integer showId;
    private LocalDate showDate;
    private LocalTime showTime;
    private String movieName;
    private String theaterName;
    private List<ShowSeatDto> seats;

    public ShowSeatResponse(Show show, List<ShowSeatDto> seats) {
        this.showId = show.getShowId();
        this.showDate = show.getDate();
        this.showTime = show.getTime();
        this.movieName = show.getMovie() != null ? show.getMovie().getMovieName() : null;
        this.theaterName = show.getTheater() != null ? show.getTheater().getName() : null;
        this.seats = seats;
    }
}
