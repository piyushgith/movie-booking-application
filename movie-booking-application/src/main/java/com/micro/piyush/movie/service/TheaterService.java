package com.micro.piyush.movie.service;

import com.micro.piyush.movie.entity.Theater;
import com.micro.piyush.movie.exception.TheaterIsExist;
import com.micro.piyush.movie.mapper.TheaterMapper;
import com.micro.piyush.movie.repository.TheaterRepository;
import com.micro.piyush.movie.request.TheaterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;

    public String addTheater(TheaterRequest theaterRequest) throws TheaterIsExist {
        if (theaterRepository.findByAddress(theaterRequest.getAddress()) != null) {
            throw new TheaterIsExist();
        }
        Theater theater = TheaterMapper.theaterDtoToTheater(theaterRequest);

        theaterRepository.save(theater);
        return "Theater has been saved Successfully";
    }


}
