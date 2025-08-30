package com.micro.piyush.movie.mapper;

import com.micro.piyush.movie.entity.Theater;
import com.micro.piyush.movie.request.TheaterRequest;

public class TheaterMapper {
    public static Theater theaterDtoToTheater(TheaterRequest theaterRequest) {
        Theater theater = Theater.builder()
                .name(theaterRequest.getName())
                .address(theaterRequest.getAddress())
                .build();
        return theater;
    }
}
