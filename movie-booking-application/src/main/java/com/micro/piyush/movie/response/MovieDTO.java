package com.micro.piyush.movie.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO implements Serializable {
    private int id;
    private String title;
    private String posterUrl;
    private String theatre;
    private List<String> timings;
    private Integer showId;
    private Integer theaterId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MovieDTO movieDTO = (MovieDTO) o;
        return id == movieDTO.id
                && Objects.equals(title, movieDTO.title)
                && Objects.equals(posterUrl, movieDTO.posterUrl)
                && Objects.equals(theatre, movieDTO.theatre)
                && Objects.equals(timings, movieDTO.timings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, posterUrl, theatre, timings);
    }
}
