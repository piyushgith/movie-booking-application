package com.micro.piyush.movie.service;

import com.micro.piyush.movie.entity.Movie;
import com.micro.piyush.movie.entity.Show;
import com.micro.piyush.movie.entity.Theater;
import com.micro.piyush.movie.enums.LocationType;
import com.micro.piyush.movie.repository.MovieRepository;
import com.micro.piyush.movie.repository.ShowRepository;
import com.micro.piyush.movie.repository.ShowSeatRepository;
import com.micro.piyush.movie.repository.TheaterRepository;
import com.micro.piyush.movie.request.ShowDto;
import com.micro.piyush.movie.request.ShowSeatDto;
import com.micro.piyush.movie.response.ShowMovieDto;
import com.micro.piyush.movie.response.ShowsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShowService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Transactional(readOnly = true)
    public ShowsResponseDto getShowsByMovieId(Integer movieId) {
        // Fetch all necessary show entities from the database
        List<Show> shows = showRepository.findByMovieId(movieId);
        Map<String, List<ShowMovieDto>> locationShowsMap = getShows(shows);
        // Finally, create the response DTO
        return new ShowsResponseDto(locationShowsMap);
    }

    @Transactional(readOnly = true)
    public ShowsResponseDto getShowsByLocation(String location) {
        LocationType locationType = LocationType.fromString(location);
        // Fetch all necessary show entities from the database
        List<Show> shows = showRepository.findShowsByTheaterLocation(locationType);
        Map<String, List<ShowMovieDto>> locationShowsMap = getShows(shows);
        // Finally, create the response DTO
        return new ShowsResponseDto(locationShowsMap);
    }

    private static Map<String, List<ShowMovieDto>> getShows(List<Show> shows) {
        // Create a map to hold the DTO data
        Map<String, List<ShowMovieDto>> locationShowsMap = new HashMap<>();

        // Iterate over the results and populate the DTO
        for (Show show : shows) {
            String location = show.getTheater().getLocation().toString();

            // Get or create the Movie DTO list for this theater
            List<ShowMovieDto> moviesInTheater = locationShowsMap.getOrDefault(location, new ArrayList<>());

            // Check if the movie is already in the list
            boolean movieExists = false;
            for (ShowMovieDto showMovieDto : moviesInTheater) {
                if (showMovieDto.getId() == show.getMovie().getId()) {
                    // Add the new showtime to the existing movie
                    List<String> temp = showMovieDto.getTimings();
                    temp.add(show.getTime().toString());
                    temp = convertAndSortTimes(temp);
                    showMovieDto.setTimings(temp);
                    movieExists = true;
                    break;
                }
            }

            // If the movie doesn't exist, create a new DTO and add it
            if (!movieExists) {
                ShowMovieDto showMovieDto = new ShowMovieDto();
                showMovieDto.setId(show.getMovie().getId());
                showMovieDto.setShowId(show.getShowId());
                showMovieDto.setTheaterId(show.getTheater().getId());
                showMovieDto.setTitle(show.getMovie().getMovieName());
                showMovieDto.setPosterUrl("https://placehold.co/400x600/702963/FFFFFF?text=\n+\n"
                        + show.getMovie().getMovieName());
                showMovieDto.setTheatre(show.getTheater().getName());
                List<String> timings = new ArrayList<>();
                timings.add(show.getTime().toString());
                showMovieDto.setTimings(timings);
                moviesInTheater.add(showMovieDto);
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



    // CREATE
    @Transactional
    public Show createShow(ShowDto showDto) {
        
        Movie movie = movieRepository.findById(showDto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Theater theater = theaterRepository.findById(showDto.getTheaterId())
                .orElseThrow(() -> new RuntimeException("Theater not found"));

        Show show = new Show();
        show.setDate(showDto.getDate());
        show.setTime(showDto.getTime());
        show.setMovie(movie);
        show.setTheater(theater);

        return showRepository.save(show);
    }

    // READ ALL with details
    @Transactional(readOnly = true)
    public List<ShowDto> getAllShowsWithDetails() {
        List<Show> shows = showRepository.findAllShowsWithDetails();
        return shows.stream().map(s->convertToShowDto(s,false)).collect(Collectors.toList());
    }

    // READ BY ID with details and seats
    @Transactional(readOnly = true)
    public ShowDto getShowWithDetails(Integer id) {
        Show show = showRepository.findShowWithDetails(id);
        if (show == null) {
            throw new RuntimeException("Show not found");
        }
        return convertToShowDto(show,false);
    }

    // UPDATE
    @Transactional
    public Show updateShow(Integer id, ShowDto showDto) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        Movie movie = movieRepository.findById(showDto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Theater theater = theaterRepository.findById(showDto.getTheaterId())
                .orElseThrow(() -> new RuntimeException("Theater not found"));

        show.setDate(showDto.getDate());
        show.setTime(showDto.getTime());
        show.setMovie(movie);
        show.setTheater(theater);

        return showRepository.save(show);
    }

    // DELETE
    @Transactional
    public void deleteShow(Integer id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found"));
        showRepository.delete(show);
    }

    // Get shows by movie
    @Transactional(readOnly = true)
    public List<ShowDto> getShowDtoByMovieId(Integer movieId) {
        List<Show> shows = showRepository.findByMovieId(movieId);
        return shows.stream().map(s->convertToShowDto(s,false)).collect(Collectors.toList());
    }

    // Get shows by theater
    @Transactional(readOnly = true)
    public List<ShowDto> getShowsByTheaterId(Integer theaterId) {
        List<Show> shows = showRepository.findByTheaterId(theaterId);
        return shows.stream().map(s->convertToShowDto(s,false)).collect(Collectors.toList());
    }

    // Helper method to convert Show to ShowDto
    private ShowDto convertToShowDto(Show show,boolean includeSeats) {
        ShowDto dto = new ShowDto();
        dto.setShowId(show.getShowId());
        dto.setDate(show.getDate());
        dto.setTime(show.getTime());
        dto.setMovieId(show.getMovie() != null ? show.getMovie().getId() : null);
        dto.setTheaterId(show.getTheater() != null ? show.getTheater().getId() : null);
        dto.setMovieName(show.getMovie() != null ? show.getMovie().getMovieName() : null);
        dto.setTheaterName(show.getTheater() != null ? show.getTheater().getName() : null);
        dto.setTheaterLocation(show.getTheater() != null ? show.getTheater().getLocation().name() : null);
        dto.setDuration(show.getMovie() != null ? show.getMovie().getDuration() : null);

        // Convert show seats
        if (show.getShowSeats() != null && includeSeats) {
            List<ShowSeatDto> seatDtos = show.getShowSeats().stream()
                    .map(seat -> new ShowSeatDto(
                            seat.getId(),
                            seat.getSeatNo(),
                            seat.getSeatType() != null ? seat.getSeatType().toString() : null,
                            seat.getPrice(),
                            seat.getIsAvailable(),
                            seat.getIsFoodContains(),
                            seat.getIsAvailable() == true ? "AVAILABLE" : "BOOKED"
                    )).collect(Collectors.toList());
            dto.setShowSeats(seatDtos);
        }
        return dto;
    }
}
