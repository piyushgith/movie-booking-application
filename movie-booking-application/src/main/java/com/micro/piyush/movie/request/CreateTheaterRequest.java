package com.micro.piyush.movie.request;

import com.micro.piyush.movie.enums.LocationType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTheaterRequest {
    private String address;

    @NotBlank(message = "Location is required")
    private LocationType location;

    @NotBlank(message = "Theater name is required")
    private String name;
}
