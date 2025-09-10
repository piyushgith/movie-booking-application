package com.micro.piyush.movie.service;


import com.micro.piyush.movie.entity.Show;
import com.micro.piyush.movie.entity.ShowSeat;
import com.micro.piyush.movie.repository.ShowRepository;
import com.micro.piyush.movie.repository.ShowSeatRepository;
import com.micro.piyush.movie.request.ShowSeatDto;
import com.micro.piyush.movie.response.ShowSeatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ShowSeatService {

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private ShowRepository showRepository;

    public List<ShowSeatDto> getSeatsForShow(Integer showId) {
        // Validate if show exists
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found with ID: " + showId));

        // Get all seats for the show
        List<ShowSeat> seats = showSeatRepository.findByShowIdOrderBySeatNo(showId);

        // Convert to DTOs
        return seats.stream()
                .map(ShowSeatDto::new)
                .collect(Collectors.toList());
    }

    // Enhanced method with show details
    public ShowSeatResponse getShowSeatDetails(Integer showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found with ID: " + showId));

        List<ShowSeat> seats = showSeatRepository.findByShowIdWithShowDetails(showId);

        List<ShowSeatDto> seatDtos = seats.stream()
                .map(ShowSeatDto::new)
                .collect(Collectors.toList());

        return new ShowSeatResponse(show, seatDtos);
    }

    // Method to get only available seats
    public List<ShowSeatDto> getAvailableSeatsForShow(Integer showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found with ID: " + showId));

        List<ShowSeat> seats = showSeatRepository.findByShowIdOrderBySeatNo(showId);

        return seats.stream()
                .filter(ShowSeat::getIsAvailable)
                .map(ShowSeatDto::new)
                .collect(Collectors.toList());
    }
}
