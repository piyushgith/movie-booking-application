package com.micro.piyush.movie.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JWTTokenResponse {
    private String token;
    private String role;
    private Integer userId;
    private String emailId;
}
