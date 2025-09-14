package com.micro.piyush.movie.controller;


import com.micro.piyush.movie.request.ShowSeatDto;
import com.micro.piyush.movie.response.ShowSeatResponse;
import com.micro.piyush.movie.service.ShowSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows_seat")
@CrossOrigin(origins = "*")
public class ShowSeatController {

    @Autowired
    private ShowSeatService showSeatService;

    @GetMapping("/{showId}/seats")
    public ResponseEntity<List<ShowSeatDto>> getSeatsForShow(@PathVariable Integer showId) {
        List<ShowSeatDto> seats = showSeatService.getSeatsForShow(showId);
        return ResponseEntity.ok(seats);
    }

    @GetMapping("/{showId}/seats/details")
    public ResponseEntity<ShowSeatResponse> getShowSeatDetails(@PathVariable Integer showId) {
        ShowSeatResponse response = showSeatService.getShowSeatDetails(showId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{showId}/seats/available")
    public ResponseEntity<List<ShowSeatDto>> getAvailableSeats(@PathVariable Integer showId) {
        List<ShowSeatDto> availableSeats = showSeatService.getAvailableSeatsForShow(showId);
        return ResponseEntity.ok(availableSeats);
    }
}
