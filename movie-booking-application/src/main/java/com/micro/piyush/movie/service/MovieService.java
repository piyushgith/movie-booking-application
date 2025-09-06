package com.micro.piyush.movie.service;

import com.micro.piyush.movie.entity.Movie;
import com.micro.piyush.movie.exception.MovieAlreadyExist;
import com.micro.piyush.movie.exception.MovieDoesNotExists;
import com.micro.piyush.movie.mapper.MovieMapper;
import com.micro.piyush.movie.repository.MovieRepository;
import com.micro.piyush.movie.request.MovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public String addMovie(MovieRequest movieRequest) {
        Movie movieByName = getMovie(movieRequest);

        if (movieByName != null && movieByName.getLanguage().equals(movieRequest.getLanguageType())) {
            throw new MovieAlreadyExist();
        }

        Movie movie = MovieMapper.movieDtoToMovie(movieRequest);
        movieRepository.save(movie);

        return "The movie has been added successfully";
    }

    private Movie getMovie(MovieRequest movieRequest) {
        Movie movieByName = movieRepository.findByMovieName(movieRequest.getMovieName());
        return movieByName;
    }

    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    public String updateMovie(MovieRequest movieRequest) {
        if (getMovie(movieRequest) == null) {
            throw new MovieDoesNotExists();
        }
        Movie movie = MovieMapper.movieDtoToMovie(movieRequest);
        movieRepository.save(movie);
        return "The movie has been updated successfully";
    }


    public String deleteMovie(MovieRequest movieRequest) {
        //TODO: Later not needed as of now
        return "Removed Movie";
    }
}