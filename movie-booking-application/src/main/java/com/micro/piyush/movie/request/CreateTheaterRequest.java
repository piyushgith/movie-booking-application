package com.micro.piyush.movie.request;

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
    private String location;

    @NotBlank(message = "Theater name is required")
    private String name;
}
