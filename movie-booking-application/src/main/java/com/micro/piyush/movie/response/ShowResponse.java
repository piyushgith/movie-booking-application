package com.micro.piyush.movie.response;


import com.micro.piyush.movie.request.ShowDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowResponse {
    private List<ShowDto> shows;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;
}
