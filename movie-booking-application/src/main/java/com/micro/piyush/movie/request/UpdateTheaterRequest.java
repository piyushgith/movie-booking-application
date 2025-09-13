package com.micro.piyush.movie.request;


import com.micro.piyush.movie.enums.LocationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTheaterRequest {
    private String address;
    private LocationType location;
    private String name;
}