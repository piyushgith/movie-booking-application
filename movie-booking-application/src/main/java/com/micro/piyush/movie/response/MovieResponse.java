package com.micro.piyush.movie.response;


import com.micro.piyush.movie.request.MovieDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MovieResponse {
    private List<MovieDto> movies;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;

    public MovieResponse(List<MovieDto> movies) {
        this.movies = movies;
        this.totalElements = movies.size();
        this.totalPages = 1;
        this.currentPage = 0;
        this.pageSize = movies.size();
    }

    public MovieResponse(List<MovieDto> movies, long totalElements, int totalPages, int currentPage, int pageSize) {
        this.movies = movies;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}
