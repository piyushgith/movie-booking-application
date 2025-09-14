package com.micro.piyush.movie.controller;

import com.micro.piyush.movie.enums.LocationType;
import com.micro.piyush.movie.request.CreateTheaterRequest;
import com.micro.piyush.movie.request.TheaterDto;
import com.micro.piyush.movie.request.UpdateTheaterRequest;
import com.micro.piyush.movie.response.TheaterResponse;
import com.micro.piyush.movie.service.TheaterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/theaters")
@CrossOrigin(origins = "*")
//@Validated
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    // Create a new theater
    @PostMapping
    public ResponseEntity<TheaterDto> createTheater(@Valid @RequestBody CreateTheaterRequest request) {
        TheaterDto theater = theaterService.createTheater(request);
        return new ResponseEntity<>(theater, HttpStatus.CREATED);
    }

    // Get theater by ID
    @GetMapping("/{id}")
    public ResponseEntity<TheaterDto> getTheaterById(@PathVariable Integer id) {
        TheaterDto theater = theaterService.getTheaterById(id);
        return ResponseEntity.ok(theater);
    }

    // Get all theaters with pagination
    @GetMapping
    public ResponseEntity<TheaterResponse> getAllTheaters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        TheaterResponse response = theaterService.getAllTheaters(pageable);
        return ResponseEntity.ok(response);
    }

    // Update theater
    @PutMapping("/{id}")
    public ResponseEntity<TheaterDto> updateTheater(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateTheaterRequest request) {
        TheaterDto theater = theaterService.updateTheater(id, request);
        return ResponseEntity.ok(theater);
    }

    // Delete theater
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheater(@PathVariable Integer id) {
        theaterService.deleteTheater(id);
        return ResponseEntity.noContent().build();
    }

    // Search theaters
    @GetMapping("/search")
    public ResponseEntity<TheaterResponse> searchTheaters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocationType location,
            @RequestParam(required = false) String address,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        TheaterResponse response = theaterService.searchTheaters(name, location, address, pageable);
        return ResponseEntity.ok(response);
    }

    // Get theaters by location
    @GetMapping("/location/{location}")
    public ResponseEntity<List<TheaterDto>> getTheatersByLocation(@PathVariable LocationType location) {
        List<TheaterDto> theaters = theaterService.getTheatersByLocation(location);
        return ResponseEntity.ok(theaters);
    }

    // Get theaters by location containing text
    @GetMapping("/location/search")
    public ResponseEntity<List<TheaterDto>> getTheatersByLocationContaining(
            @RequestParam String location) {
        List<TheaterDto> theaters = theaterService.getTheatersByLocationContaining(location);
        return ResponseEntity.ok(theaters);
    }

    // Get theaters by name containing text
    @GetMapping("/search/name")
    public ResponseEntity<List<TheaterDto>> getTheatersByNameContaining(
            @RequestParam String name) {
        List<TheaterDto> theaters = theaterService.getTheatersByNameContaining(name);
        return ResponseEntity.ok(theaters);
    }

    // Get all theaters (no pagination)
    @GetMapping("/all")
    public ResponseEntity<List<TheaterDto>> getAllTheatersNoPagination() {
        List<TheaterDto> theaters = theaterService.getAllTheaters();
        return ResponseEntity.ok(theaters);
    }

    // Get theaters with shows count
    @GetMapping("/with-shows")
    public ResponseEntity<List<TheaterDto>> getTheatersWithShowsCount() {
        List<TheaterDto> theaters = theaterService.getTheatersWithShowsCount();
        return ResponseEntity.ok(theaters);
    }

}