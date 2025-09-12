package com.micro.piyush.movie.response;


import com.micro.piyush.movie.request.UserTicketDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTicketsResponse {
    private List<UserTicketDto> tickets;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;

    public UserTicketsResponse(List<UserTicketDto> tickets) {
        this.tickets = tickets;
        this.totalElements = tickets.size();
        this.totalPages = 1;
        this.currentPage = 0;
        this.pageSize = tickets.size();
    }
}
