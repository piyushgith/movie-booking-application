package com.micro.piyush.movie.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShowsResponseDto implements Serializable {
    private Map<String, List<ShowMovieDto>> locations;
}
