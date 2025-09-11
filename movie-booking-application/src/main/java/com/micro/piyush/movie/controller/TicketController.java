package com.micro.piyush.movie.controller;

import com.micro.piyush.movie.request.BookTicketRequest;
import com.micro.piyush.movie.request.UserTicketDto;
import com.micro.piyush.movie.response.BookTicketResponse;
import com.micro.piyush.movie.response.UserTicketsResponse;
import com.micro.piyush.movie.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/book")
    public ResponseEntity<BookTicketResponse> bookTicket(@RequestBody BookTicketRequest request) {
        BookTicketResponse response = ticketService.bookShowTicket(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserTicketsResponse> getUserTickets(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "bookedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        UserTicketsResponse response = ticketService.getUserTickets(userId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}/all")
    public ResponseEntity<UserTicketsResponse> getAllUserTickets(@PathVariable Integer userId) {
        UserTicketsResponse response = ticketService.getUserTickets(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{ticketId}/user/{userId}")
    public ResponseEntity<UserTicketDto> getTicketById(
            @PathVariable Integer userId,
            @PathVariable Integer ticketId) {

        UserTicketDto ticket = ticketService.getTicketById(userId, ticketId);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> getUserTicketCount(@PathVariable Integer userId) {
        long count = ticketService.getUserTicketCount(userId);
        return ResponseEntity.ok(count);
    }

}

