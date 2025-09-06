package com.micro.piyush.movie.controller;


import com.micro.piyush.movie.entity.Movie;
import com.micro.piyush.movie.request.MovieRequest;
import com.micro.piyush.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "*")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/addNew")
    public ResponseEntity<String> addMovie(@RequestBody MovieRequest movieRequest) {
        try {
            String result = movieService.addMovie(movieRequest);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAllMovies")
    public ResponseEntity<List<Movie>> findAllMovies() {
        return new ResponseEntity<>(movieService.findAllMovies(), HttpStatus.OK);
    }

    @PutMapping("/updateMovie")
    public ResponseEntity<String> updateMovie(@RequestBody MovieRequest movieRequest){
        return new ResponseEntity<>(movieService.updateMovie(movieRequest), HttpStatus.OK);
    }

    @DeleteMapping("/deleteMovie")
    public ResponseEntity<String> deleteMovie(@RequestBody MovieRequest movieRequest){
        return new ResponseEntity<>(movieService.deleteMovie(movieRequest), HttpStatus.OK);
    }

}
