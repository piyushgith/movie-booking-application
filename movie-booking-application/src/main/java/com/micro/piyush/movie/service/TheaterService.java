package com.micro.piyush.movie.service;

import com.micro.piyush.movie.entity.Theater;
import com.micro.piyush.movie.enums.LocationType;
import com.micro.piyush.movie.exception.TheaterIsExist;
import com.micro.piyush.movie.mapper.TheaterMapper;
import com.micro.piyush.movie.repository.TheaterRepository;
import com.micro.piyush.movie.request.CreateTheaterRequest;
import com.micro.piyush.movie.request.TheaterDto;
import com.micro.piyush.movie.request.TheaterRequest;
import com.micro.piyush.movie.request.UpdateTheaterRequest;
import com.micro.piyush.movie.response.TheaterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    // Create a new theater
    @Transactional
    public TheaterDto createTheater(CreateTheaterRequest request) {
        // Check if theater name already exists
        if (theaterRepository.findByNameIgnoreCase(request.getName()).isPresent()) {
            throw new RuntimeException("Theater with name '" + request.getName() + "' already exists");
        }

        Theater theater = Theater.builder()
                .address(request.getAddress())
                .location(request.getLocation())
                .name(request.getName())
                .build();

        Theater savedTheater = theaterRepository.save(theater);
        return new TheaterDto(savedTheater);
    }

    // Get theater by ID
    @Transactional(readOnly = true)
    public TheaterDto getTheaterById(Integer id) {
        Theater theater = theaterRepository.findByIdWithShowsAndSeats(id)
                .orElseThrow(() -> new RuntimeException("Theater not found with ID: " + id));
        return new TheaterDto(theater);
    }

    // Get all theaters with pagination
    @Transactional(readOnly = true)
    public TheaterResponse getAllTheaters(Pageable pageable) {
        Page<Theater> theaterPage = theaterRepository.findAll(pageable);
        List<TheaterDto> theaterDtos = theaterPage.getContent().stream()
                .map(TheaterDto::new)
                .collect(Collectors.toList());

        return new TheaterResponse(
                theaterDtos,
                theaterPage.getTotalElements(),
                theaterPage.getTotalPages(),
                theaterPage.getNumber(),
                theaterPage.getSize()
        );
    }

    // Update theater
    @Transactional
    public TheaterDto updateTheater(Integer id, UpdateTheaterRequest request) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theater not found with ID: " + id));

        // Check if theater name already exists (excluding current theater)
        if (request.getName() != null &&
                !request.getName().equals(theater.getName()) &&
                theaterRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
            throw new RuntimeException("Theater with name '" + request.getName() + "' already exists");
        }

        // Update fields if provided
        if (request.getAddress() != null) theater.setAddress(request.getAddress());
        if (request.getLocation() != null) theater.setLocation(request.getLocation());
        if (request.getName() != null) theater.setName(request.getName());

        Theater updatedTheater = theaterRepository.save(theater);
        return new TheaterDto(updatedTheater);
    }

    // Delete theater
    public void deleteTheater(Integer id) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theater not found with ID: " + id));

        // Check if theater has shows or seats (business logic - prevent deletion if they exist)
        if (theater.getShows() != null && !theater.getShows().isEmpty()) {
            throw new RuntimeException("Cannot delete theater with existing shows. Please delete shows first.");
        }

        if (theater.getShowSeats() != null && !theater.getShowSeats().isEmpty()) {
            throw new RuntimeException("Cannot delete theater with existing show seats. Please delete show seats first.");
        }

        theaterRepository.delete(theater);
    }

    // Search theaters
    @Transactional(readOnly = true)
    public TheaterResponse searchTheaters(String name, LocationType location, String address, Pageable pageable) {
        Page<Theater> theaterPage = theaterRepository.searchTheaters(name, location, address, pageable);
        List<TheaterDto> theaterDtos = theaterPage.getContent().stream()
                .map(TheaterDto::new)
                .collect(Collectors.toList());

        return new TheaterResponse(
                theaterDtos,
                theaterPage.getTotalElements(),
                theaterPage.getTotalPages(),
                theaterPage.getNumber(),
                theaterPage.getSize()
        );
    }

    // Get theaters by location
    @Transactional(readOnly = true)
    public List<TheaterDto> getTheatersByLocation(LocationType location) {
        List<Theater> theaters = theaterRepository.findByLocation(location);
        return theaters.stream()
                .map(TheaterDto::new)
                .collect(Collectors.toList());
    }

    // Get theaters by location containing text
    @Transactional(readOnly = true)
    public List<TheaterDto> getTheatersByLocationContaining(String location) {
        List<Theater> theaters = theaterRepository.findByLocationContaining(location);
        return theaters.stream()
                .map(TheaterDto::new)
                .collect(Collectors.toList());
    }

    // Get theaters by name containing text
    @Transactional(readOnly = true)
    public List<TheaterDto> getTheatersByNameContaining(String name) {
        List<Theater> theaters = theaterRepository.findByNameContaining(name);
        return theaters.stream()
                .map(TheaterDto::new)
                .collect(Collectors.toList());
    }

    // Get all theaters (no pagination)
    @Transactional(readOnly = true)
    public List<TheaterDto> getAllTheaters() {
        List<Theater> theaters = theaterRepository.findAll();
        return theaters.stream()
                .map(TheaterDto::new)
                .collect(Collectors.toList());
    }

    // Get theater with shows count
    @Transactional(readOnly = true)
    public List<TheaterDto> getTheatersWithShowsCount() {
        List<Theater> theaters = theaterRepository.findAllWithShows();
        return theaters.stream()
                .map(TheaterDto::new)
                .collect(Collectors.toList());
    }


}
