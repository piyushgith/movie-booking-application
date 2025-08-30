package com.micro.piyush.movie.response;

import com.micro.piyush.movie.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String name;
    private Integer age;
    private Gender gender;
    private String address;
}
