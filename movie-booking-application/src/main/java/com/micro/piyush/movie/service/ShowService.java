package com.micro.piyush.movie.service;

import com.micro.piyush.movie.entity.*;
import com.micro.piyush.movie.enums.LocationType;
import com.micro.piyush.movie.enums.SeatType;
import com.micro.piyush.movie.exception.MovieDoesNotExists;
import com.micro.piyush.movie.exception.ShowDoesNotExists;
import com.micro.piyush.movie.exception.TheaterDoesNotExists;
import com.micro.piyush.movie.mapper.ShowMapper;
import com.micro.piyush.movie.repository.*;
import com.micro.piyush.movie.request.ShowDto;
import com.micro.piyush.movie.request.ShowRequest;
import com.micro.piyush.movie.request.ShowSearchRequest;
import com.micro.piyush.movie.request.ShowSeatRequest;
import com.micro.piyush.movie.response.MovieDTO;
import com.micro.piyush.movie.response.ShowResponse;
import com.micro.piyush.movie.response.ShowsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ShowService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ShowRepository showRepository;

    public ShowsResponseDTO getShowsByMovieId(Integer movieId) {
        // Fetch all necessary show entities from the database
        List<Show> shows = showRepository.findByMovieId(movieId);
        Map<String, List<MovieDTO>> locationShowsMap = getShows(shows);
        // Finally, create the response DTO
        return new ShowsResponseDTO(locationShowsMap);
    }

    public ShowsResponseDTO getShowsByLocation(String location) {
        LocationType locationType = LocationType.fromString(location);
        // Fetch all necessary show entities from the database
        List<Show> shows = showRepository.findShowsByTheaterLocation(locationType);
        Map<String, List<MovieDTO>> locationShowsMap = getShows(shows);
        // Finally, create the response DTO
        return new ShowsResponseDTO(locationShowsMap);
    }

    private static Map<String, List<MovieDTO>> getShows(List<Show> shows) {
        // Create a map to hold the DTO data
        Map<String, List<MovieDTO>> locationShowsMap = new HashMap<>();

        // Iterate over the results and populate the DTO
        for (Show show : shows) {
            String location = show.getTheater().getLocation().toString();

            // Get or create the Movie DTO list for this theater
            List<MovieDTO> moviesInTheater = locationShowsMap.getOrDefault(location, new ArrayList<>());

            // Check if the movie is already in the list
            boolean movieExists = false;
            for (MovieDTO movieDto : moviesInTheater) {
                if (movieDto.getId() == show.getMovie().getId()) {
                    // Add the new showtime to the existing movie
                    List<String> temp = movieDto.getTimings();
                    temp.add(show.getTime().toString());
                    temp = convertAndSortTimes(temp);
                    movieDto.setTimings(temp);
                    movieExists = true;
                    break;
                }
            }

            // If the movie doesn't exist, create a new DTO and add it
            if (!movieExists) {
                MovieDTO movieDto = new MovieDTO();
                movieDto.setId(show.getMovie().getId());
                movieDto.setShowId(show.getShowId());
                movieDto.setTheaterId(show.getTheater().getId());
                movieDto.setTitle(show.getMovie().getMovieName());
                movieDto.setPosterUrl("https://placehold.co/400x600/702963/FFFFFF?text=\n+\n"
                        + show.getMovie().getMovieName());
                movieDto.setTheatre(show.getTheater().getName());
                List<String> timings = new ArrayList<>();
                timings.add(show.getTime().toString());
                movieDto.setTimings(timings);
                moviesInTheater.add(movieDto);
            }

            // Put the updated list back into the map
            locationShowsMap.put(location, moviesInTheater);
        }
        return locationShowsMap;
    }

    /**
     * Converts a list of time strings (HH:mm:ss format) to a sorted list of
     * formatted strings (h:mm a format). The resulting list is
     * sorted in chronological order.
     *
     * @param stringTimes The list of time strings to convert.
     * @return A new List of Strings with the formatted times, sorted
     * chronologically.
     */
    public static List<String> convertAndSortTimes(List<String> stringTimes) {
        // Formatter for 24-hour format (HH:mm)
        DateTimeFormatter formatter24 = DateTimeFormatter.ofPattern("HH:mm");
        // Formatter for 12-hour format with AM/PM (h:mm a)
        DateTimeFormatter formatter12 = DateTimeFormatter.ofPattern("h:mm a");
        // Output formatter for h:mm a
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("h:mm a");

        return stringTimes.stream()
                .filter(time -> time != null && !time.trim().isEmpty()) // Filter out null or empty strings
                .map(time -> {
                    try {
                        // Try parsing as 24-hour format
                        return LocalTime.parse(time, formatter24);
                    } catch (DateTimeParseException e1) {
                        try {
                            // Try parsing as 12-hour format with AM/PM
                            return LocalTime.parse(time, formatter12);
                        } catch (DateTimeParseException e2) {
                            // Skip invalid formats by returning null
                            return null;
                        }
                    }
                })
                .filter(time -> time != null) // Remove nulls from failed parses
                .sorted() // Sort chronologically
                .map(time -> time.format(outputFormatter)) // Format to h:mm a
                .collect(Collectors.toList());
    }




    public List<ShowDto> getAllShows() {
        List<Show> shows = showRepository.findAll();
        return shows.stream()
                .map(ShowDto::new)
                .collect(Collectors.toList());
    }

    public ShowResponse getAllShows(Pageable pageable) {
        Page<Show> showPage = showRepository.findAll(pageable);
        List<ShowDto> showDtos = showPage.getContent().stream()
                .map(ShowDto::new)
                .collect(Collectors.toList());

        return new ShowResponse(
                showDtos,
                showPage.getTotalElements(),
                showPage.getTotalPages(),
                showPage.getNumber(),
                showPage.getSize()
        );
    }

    public List<ShowDto> getUpcomingShows() {
        List<Show> shows = showRepository.findUpcomingShows();
        return shows.stream()
                .map(ShowDto::new)
                .collect(Collectors.toList());
    }

/*    public List<ShowDto> getShowsByMovieId(Integer movieId) {
        List<Show> shows = showRepository.findByMovieId(movieId);
        return shows.stream()
                .map(ShowDto::new)
                .collect(Collectors.toList());
    }*/

    public List<ShowDto> getShowsByTheaterId(Integer theaterId) {
        List<Show> shows = showRepository.findByTheaterId(theaterId);
        return shows.stream()
                .map(ShowDto::new)
                .collect(Collectors.toList());
    }

    public List<ShowDto> getShowsByDate(LocalDate date) {
        List<Show> shows = showRepository.findByDate(date);
        return shows.stream()
                .map(ShowDto::new)
                .collect(Collectors.toList());
    }

    public ShowResponse searchShows(ShowSearchRequest request, Pageable pageable) {
        Page<Show> showPage = showRepository.searchShows(
                request.getMovieId(),
                request.getTheaterId(),
                request.getDate(),
                request.getMovieName(),
                request.getTheaterName(),
                request.getLocation(),
                pageable
        );

        List<ShowDto> showDtos = showPage.getContent().stream()
                .map(ShowDto::new)
                .collect(Collectors.toList());

        return new ShowResponse(
                showDtos,
                showPage.getTotalElements(),
                showPage.getTotalPages(),
                showPage.getNumber(),
                showPage.getSize()
        );
    }

    public ShowDto getShowById(Integer showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found with ID: " + showId));
        return new ShowDto(show);
    }
}
