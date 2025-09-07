package com.micro.piyush.movie.controller;

import com.micro.piyush.movie.enums.LocationType;
import com.micro.piyush.movie.request.ShowRequest;
import com.micro.piyush.movie.request.ShowSeatRequest;
import com.micro.piyush.movie.response.ShowsResponseDTO;
import com.micro.piyush.movie.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/show")
@CrossOrigin(origins = "*")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/addNew")
    public ResponseEntity<String> addShow(@RequestBody ShowRequest showRequest) {
        try {
            String result = showService.addShow(showRequest);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/associateSeats")
    public ResponseEntity<String> associateShowSeats(@RequestBody ShowSeatRequest showSeatRequest) {
        try {
            String result = showService.associateShowSeats(showSeatRequest);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findShow/{movieId}")
    public ResponseEntity<ShowsResponseDTO> findShow(@PathVariable("movieId") Integer movieId) {
        return  new ResponseEntity<>(showService.getShowsByMovieId(movieId), HttpStatus.OK);
    }

    @GetMapping("/findShowByLocation/{location}")
    public ResponseEntity<ShowsResponseDTO> getShowsByLocation(@PathVariable("location") String location) {
        return  new ResponseEntity<>(showService.getShowsByLocation(location), HttpStatus.OK);
    }

}
