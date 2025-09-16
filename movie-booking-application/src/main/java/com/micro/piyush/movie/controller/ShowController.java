package com.micro.piyush.movie.controller;

import com.micro.piyush.movie.entity.Show;
import com.micro.piyush.movie.entity.ShowSeat;
import com.micro.piyush.movie.request.ShowDto;
import com.micro.piyush.movie.request.ShowSearchRequest;
import com.micro.piyush.movie.request.ShowSeatDto;
import com.micro.piyush.movie.response.ShowResponse;
import com.micro.piyush.movie.response.ShowsResponseDto;
import com.micro.piyush.movie.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/shows")
@CrossOrigin(origins = "*")
public class ShowController {

    @Autowired
    private ShowService showService;

    @GetMapping("/findShow/{movieId}")
    public ResponseEntity<ShowsResponseDto> findShow(@PathVariable("movieId") Integer movieId) {
        return  new ResponseEntity<>(showService.getShowsByMovieId(movieId), HttpStatus.OK);
    }

    @GetMapping("/findShowByLocation/{location}")
    public ResponseEntity<ShowsResponseDto> getShowsByLocation(@PathVariable("location") String location) {
        return  new ResponseEntity<>(showService.getShowsByLocation(location), HttpStatus.OK);
    }

/*    @PostMapping("/{showId}/seats")
    public ResponseEntity<List<ShowSeat>> createShowSeats(@PathVariable Integer showId,
                                                          @RequestBody List<ShowSeatDto> seatDTOs) {
        List<ShowSeat> seats = showService.createShowSeats(showId, seatDTOs);
        return new ResponseEntity<>(seats, HttpStatus.CREATED);
    }*/

    @PostMapping
    public ResponseEntity<Show> createShow(@RequestBody ShowDto showDto) {
        Show show = showService.createShow(showDto);
        return new ResponseEntity<>(show, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ShowDto>> getAllShows() {
        List<ShowDto> shows = showService.getAllShowsWithDetails();
        return new ResponseEntity<>(shows, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowDto> getShowById(@PathVariable Integer id) {
        ShowDto show = showService.getShowWithDetails(id);
        return new ResponseEntity<>(show, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Show> updateShow(@PathVariable Integer id, @RequestBody ShowDto showDto) {
        Show show = showService.updateShow(id, showDto);
        return new ResponseEntity<>(show, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShow(@PathVariable Integer id) {
        showService.deleteShow(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowDto>> getShowsByMovie(@PathVariable Integer movieId) {
        List<ShowDto> shows = showService.getShowDtoByMovieId(movieId);
        return new ResponseEntity<>(shows, HttpStatus.OK);
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<ShowDto>> getShowsByTheater(@PathVariable Integer theaterId) {
        List<ShowDto> shows = showService.getShowsByTheaterId(theaterId);
        return new ResponseEntity<>(shows, HttpStatus.OK);
    }
}
