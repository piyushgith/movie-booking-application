package com.micro.piyush.movie.mapper;

import com.micro.piyush.movie.entity.Movie;
import com.micro.piyush.movie.request.MovieRequest;

public class MovieMapper {

    public static Movie movieDtoToMovie(MovieRequest movieRequest) {
        Movie movie = Movie.builder()
                .movieName(movieRequest.getMovieName())
                .duration(movieRequest.getDuration())
                .genre(movieRequest.getGenreType())
                .language(movieRequest.getLanguageType())
                .releaseDate(movieRequest.getReleaseDate())
                .rating(movieRequest.getRating())
                .build();
        if (null != movieRequest.getId() || 0 == movieRequest.getId()) {
            movie.setId(movieRequest.getId());
        }
        return movie;
    }


}
