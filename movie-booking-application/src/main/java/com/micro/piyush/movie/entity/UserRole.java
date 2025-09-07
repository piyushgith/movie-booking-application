package com.micro.piyush.movie.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user_role")
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @MapsId("userId") // Maps the 'userId' field of the composite key
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // Prevent serializing the User field
    private User user;

    @Column(name = "user_role", length = 50)
    @Size(max = 50, message = "Role cannot exceed 50 characters")
    private String userRole;

}

