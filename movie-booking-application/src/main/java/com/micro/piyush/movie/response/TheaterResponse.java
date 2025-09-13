package com.micro.piyush.movie.response;

import com.micro.piyush.movie.request.TheaterDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TheaterResponse {
    private List<TheaterDto> theaters;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;

    public TheaterResponse(List<TheaterDto> theaters) {
        this.theaters = theaters;
        this.totalElements = theaters.size();
        this.totalPages = 1;
        this.currentPage = 0;
        this.pageSize = theaters.size();
    }

    public TheaterResponse(List<TheaterDto> theaters, long totalElements, int totalPages, int currentPage, int pageSize) {
        this.theaters = theaters;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}
