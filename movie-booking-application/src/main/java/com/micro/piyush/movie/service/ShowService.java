package com.micro.piyush.movie.service;

import com.micro.piyush.movie.entity.*;
import com.micro.piyush.movie.enums.LocationType;
import com.micro.piyush.movie.enums.SeatType;
import com.micro.piyush.movie.exception.MovieDoesNotExists;
import com.micro.piyush.movie.exception.ShowDoesNotExists;
import com.micro.piyush.movie.exception.TheaterDoesNotExists;
import com.micro.piyush.movie.mapper.ShowMapper;
import com.micro.piyush.movie.repository.*;
import com.micro.piyush.movie.request.ShowRequest;
import com.micro.piyush.movie.request.ShowSeatRequest;
import com.micro.piyush.movie.response.MovieDTO;
import com.micro.piyush.movie.response.ShowsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
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


    public String addShow(ShowRequest showRequest) {
        Show show = ShowMapper.showDtoToShow(showRequest);

        Optional<Movie> movieOpt = movieRepository.findById(showRequest.getMovieId());

        if (movieOpt.isEmpty()) {
            throw new MovieDoesNotExists();
        }

        Optional<Theater> theaterOpt = theaterRepository.findById(showRequest.getTheaterId());

        if (theaterOpt.isEmpty()) {
            throw new TheaterDoesNotExists();
        }

        Theater theater = theaterOpt.get();
        Movie movie = movieOpt.get();

        show.setMovie(movie);
        show.setTheater(theater);
        show = showRepository.save(show);

//        movie.getShows().add(show);
//        theater.getShowList().add(show);

        movieRepository.save(movie);
        theaterRepository.save(theater);

        return "Show has been added Successfully";
    }

    public String associateShowSeats(ShowSeatRequest showSeatRequest) throws ShowDoesNotExists {
        Optional<Show> showOpt = showRepository.findById(showSeatRequest.getShowId());

        if (showOpt.isEmpty()) {
            throw new ShowDoesNotExists();
        }

        Show show = showOpt.get();
        Theater theater = show.getTheater();

//        List<TheaterSeat> theaterSeatList = theater.getTheaterSeatList();
//        List<ShowSeat> showSeatList = show.getShowSeatList();
//
//        for (TheaterSeat theaterSeat : theaterSeatList) {
//            ShowSeat showSeat = new ShowSeat();
//            showSeat.setSeatNo(theaterSeat.getSeatNo());
//            showSeat.setSeatType(theaterSeat.getSeatType());
//
//            if (showSeat.getSeatType().equals(SeatType.CLASSIC)) {
//                showSeat.setPrice((showSeatRequest.getPriceOfClassicSeat()));
//            } else {
//                showSeat.setPrice(showSeatRequest.getPriceOfPremiumSeat());
//            }
//
//            showSeat.setShow(show);
//            showSeat.setIsAvailable(Boolean.TRUE);
//            showSeat.setIsFoodContains(Boolean.FALSE);
//
//            showSeatList.add(showSeat);
//        }

        showRepository.save(show);
        return "Show seats have been associated successfully";
    }
}
