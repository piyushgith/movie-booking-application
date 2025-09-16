package com.micro.piyush.movie.request;


import com.micro.piyush.movie.entity.Show;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowDto {
    private Integer showId;
    private Integer theaterId;
    private LocalDate date;
    private LocalTime time;
    private String movieName;
    private String theaterName;
    private String theaterLocation;
    private Integer duration; // Movie duration
    private List<ShowSeatDto> showSeats;


    public ShowDto(Show show) {
        this.showId = show.getShowId();
        this.date = show.getDate();
        this.time = show.getTime();
        this.movieName = show.getMovie() != null ? show.getMovie().getMovieName() : null;
        this.theaterId = show.getTheater() != null ? show.getTheater().getId() : null;
        this.theaterName = show.getTheater() != null ? show.getTheater().getName() : null;
        this.theaterLocation = show.getTheater() != null ? show.getTheater().getLocation().toString() : null;
        this.duration = show.getMovie() != null ? show.getMovie().getDuration() : null;
    }

}
