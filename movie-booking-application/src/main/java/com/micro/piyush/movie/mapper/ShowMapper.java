package com.micro.piyush.movie.mapper;

import com.micro.piyush.movie.entity.Show;
import com.micro.piyush.movie.request.ShowRequest;


public class ShowMapper {
    public static Show showDtoToShow(ShowRequest showRequest) {
        Show show = Show.builder()
                .time(showRequest.getShowStartTime())
                .date(showRequest.getShowDate())
                .build();
        return show;
    }
}
