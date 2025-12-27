package com.micro.piyush.movie.request;

import com.micro.piyush.movie.enums.GenderType;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // Required for Jackson deserialization
@AllArgsConstructor // Required for @Builder
@Schema(description = "Represents the request payload for creating or updating a user.")
public class UserRequest {

    @Schema(description = "Unique identifier of the user.", example = "101")
    private Integer id;

    @Schema(description = "Full name of the user.", example = "John Doe", required = true)
    private String name;

    // Lombok's @Builder.Default sets a runtime default when using the builder
    @Builder.Default
    @Schema(description = "Age of the user.", example = "30")
    private Integer age = 25; // Runtime default is 25

    @Schema(description = "Physical address.", example = "123 Main St, Anytown, USA")
    private String address;

    @Schema(description = "Mobile phone number.", example = "91234567")
    private String mobileNo;

    @Schema(description = "Email address of the user.", example = "test@gmail.com", required = true)
    private String emailId;

    @Builder.Default
    @Schema(description = "Gender of the user.", example = "MALE")
    private GenderType genderType = GenderType.MALE; // Runtime default is MALE

    @Builder.Default
    @Schema(description = "User's role in the system.", example = "USER")
    private String role = "USER"; // Runtime default is "USER"

    @Schema(description = "User's password.", example = "password")
    private String password;
}
