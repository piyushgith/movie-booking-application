package com.micro.piyush.movie.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowMovieDto implements Serializable {
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
        ShowMovieDto showMovieDto = (ShowMovieDto) o;
        return id == showMovieDto.id
                && Objects.equals(title, showMovieDto.title)
                && Objects.equals(posterUrl, showMovieDto.posterUrl)
                && Objects.equals(theatre, showMovieDto.theatre)
                && Objects.equals(timings, showMovieDto.timings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, posterUrl, theatre, timings);
    }
}
