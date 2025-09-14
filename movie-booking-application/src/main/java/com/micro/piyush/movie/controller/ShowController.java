package com.micro.piyush.movie.controller;

import com.micro.piyush.movie.request.ShowDto;
import com.micro.piyush.movie.request.ShowSearchRequest;
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

    @GetMapping
    public ResponseEntity<ShowResponse> getAllShows(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        ShowResponse response = showService.getAllShows(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<ShowDto>> getUpcomingShows() {
        List<ShowDto> shows = showService.getUpcomingShows();
        return ResponseEntity.ok(shows);
    }

    @GetMapping("/{showId}")
    public ResponseEntity<ShowDto> getShowById(@PathVariable Integer showId) {
        ShowDto show = showService.getShowById(showId);
        return ResponseEntity.ok(show);
    }

/*    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowDto>> getShowsByMovieId(@PathVariable Integer movieId) {
        List<ShowDto> shows = showService.getShowsByMovieId(movieId);
        return ResponseEntity.ok(shows);
    }*/

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<ShowDto>> getShowsByTheaterId(@PathVariable Integer theaterId) {
        List<ShowDto> shows = showService.getShowsByTheaterId(theaterId);
        return ResponseEntity.ok(shows);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<ShowDto>> getShowsByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ShowDto> shows = showService.getShowsByDate(date);
        return ResponseEntity.ok(shows);
    }

    @PostMapping("/search")
    public ResponseEntity<ShowResponse> searchShows(
            @RequestBody ShowSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("date").ascending());
        ShowResponse response = showService.searchShows(request, pageable);
        return ResponseEntity.ok(response);
    }
   

    @GetMapping("/findShow/{movieId}")
    public ResponseEntity<ShowsResponseDto> findShow(@PathVariable("movieId") Integer movieId) {
        return  new ResponseEntity<>(showService.getShowsByMovieId(movieId), HttpStatus.OK);
    }

    @GetMapping("/findShowByLocation/{location}")
    public ResponseEntity<ShowsResponseDto> getShowsByLocation(@PathVariable("location") String location) {
        return  new ResponseEntity<>(showService.getShowsByLocation(location), HttpStatus.OK);
    }

}
