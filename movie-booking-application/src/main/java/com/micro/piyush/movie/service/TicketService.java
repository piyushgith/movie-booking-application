package com.micro.piyush.movie.service;

import com.micro.piyush.movie.entity.*;
import com.micro.piyush.movie.exception.SeatsNotAvailable;
import com.micro.piyush.movie.exception.ShowDoesNotExists;
import com.micro.piyush.movie.exception.UserDoesNotExists;
import com.micro.piyush.movie.mapper.TicketMapper;
import com.micro.piyush.movie.repository.ShowRepository;
import com.micro.piyush.movie.repository.ShowSeatRepository;
import com.micro.piyush.movie.repository.TicketRepository;
import com.micro.piyush.movie.repository.UserRepository;
import com.micro.piyush.movie.request.BookTicketRequest;
import com.micro.piyush.movie.request.TicketRequest;
import com.micro.piyush.movie.request.UserTicketDto;
import com.micro.piyush.movie.response.BookTicketResponse;
import com.micro.piyush.movie.response.TicketResponse;
import com.micro.piyush.movie.response.UserTicketsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Transactional
    public BookTicketResponse bookShowTicket(BookTicketRequest request) {
        // Fetch user
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch show
        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        // Validate seats
        List<ShowSeat> requestedSeats = showSeatRepository.findByIdInAndIsAvailableTrue(request.getSeatIds());
        if (requestedSeats.size() != request.getSeatIds().size()) {
            throw new RuntimeException("Some seats are not available");
        }

        // Calculate total amount
        int totalAmount = requestedSeats.stream().mapToInt(ShowSeat::getPrice).sum();

        // Create ticket
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setShow(show);
        ticket.setBookedAt(java.time.LocalDateTime.now());
        ticket.setTotalTicketsPrice(totalAmount);

        // Save ticket first to get ID
        ticket = ticketRepository.save(ticket);

        // Create ticket-seat mappings
        List<TicketSeat> ticketSeats = new ArrayList<>();
        for (ShowSeat seat : requestedSeats) {
            seat.setIsAvailable(false); // Mark seat as booked
            TicketSeat ticketSeat = new TicketSeat();
            ticketSeat.setId(new TicketSeatId(null, seat.getId()));
            ticketSeat.setTicket(ticket);
            ticketSeat.setShowSeat(seat);
            ticketSeats.add(ticketSeat);
        }

        ticket.setTicketSeats(ticketSeats);
        showSeatRepository.saveAll(requestedSeats); // Update seat availability
        ticketRepository.save(ticket); // Save ticket with ticket-seats

        return new BookTicketResponse().builder()
                .bookingId(ticket.getTicketId())
                .movieName(show.getMovie().getMovieName())
                .showTime(show.getTime().toString())
                .message("Ticket booked successfully")
                .bookedSeats(requestedSeats.stream().map(ShowSeat::getSeatNo).toList())
                .totalAmount(totalAmount)
                .build();
    }

    @Transactional(readOnly = true)
    public UserTicketsResponse getUserTickets(Integer userId) {
        // Validate user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Get all tickets for the user
        List<Ticket> tickets = ticketRepository.findTicketsByUserId(userId);

        // Convert to DTOs
        List<UserTicketDto> ticketDtos = tickets.stream()
                .map(UserTicketDto::new)
                .collect(Collectors.toList());

        return new UserTicketsResponse(ticketDtos);
    }

    @Transactional(readOnly = true)
    public UserTicketsResponse getUserTickets(Integer userId, Pageable pageable) {
        // Validate user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Get paginated tickets for the user
        Page<Ticket> ticketPage = ticketRepository.findTicketsByUserId(userId, pageable);

        // Convert to DTOs
        List<UserTicketDto> ticketDtos = ticketPage.getContent().stream()
                .map(UserTicketDto::new)
                .collect(Collectors.toList());

        return new UserTicketsResponse(
                ticketDtos,
                ticketPage.getTotalElements(),
                ticketPage.getTotalPages(),
                ticketPage.getNumber(),
                ticketPage.getSize()
        );
    }

    @Transactional(readOnly = true)
    public UserTicketDto getTicketById(Integer userId, Integer ticketId) {
        // Validate user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Get specific ticket for the user
        Ticket ticket = ticketRepository.findTicketByIdAndUserId(ticketId, userId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + ticketId + " for user: " + userId));

        return new UserTicketDto(ticket);
    }

    @Transactional(readOnly = true)
    public long getUserTicketCount(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        return ticketRepository.countTicketsByUserId(userId);
    }


}
