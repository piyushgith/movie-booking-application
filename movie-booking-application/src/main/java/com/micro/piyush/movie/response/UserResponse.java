package com.micro.piyush.movie.response;

import com.micro.piyush.movie.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer id;
    private String name;
    private Integer age;
    private GenderType genderType;
    private String address;
    private String mobileNo;
    private String emailId;
    private String roles;
}
