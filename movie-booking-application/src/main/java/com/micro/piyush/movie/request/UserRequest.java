package com.micro.piyush.movie.request;

import com.micro.piyush.movie.enums.GenderType;
import lombok.Data;

@Data
public class UserRequest {
    private Integer id;
    private String name;
    private Integer age;
    private String address;
    private String mobileNo;
    private String emailId;
    private GenderType genderType;
    private String roles;
    private String password;
}
