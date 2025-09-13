package com.micro.piyush.movie.request;


import com.micro.piyush.movie.entity.Theater;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheaterDto {
    private Integer id;
    private String address;
    private String location;
    private String name;
    private Integer totalShows;
    private Integer totalSeats;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TheaterDto(Theater theater) {
        this.id = theater.getId();
        this.address = theater.getAddress();
        this.location = theater.getLocation() != null ? theater.getLocation().name() : null;
        this.name = theater.getName();
        this.totalShows = theater.getShows() != null ? theater.getShows().size() : 0;
        this.totalSeats = theater.getShowSeats() != null ? theater.getShowSeats().size() : 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
