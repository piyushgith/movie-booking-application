package com.micro.piyush.movie.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.micro.piyush.movie.enums.GenderType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "address")
    private String address;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email_id", nullable = false, unique = true)
    private String emailId;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderType gender;

    @Column(name = "mobile_no", nullable = false, unique = true)
    private String mobileNo;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    // Relationships
    // A User can have many UserRoles
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // This side is serialized in a bidirectional relationship
    private Set<UserRole> userRoles = new HashSet<>();

    // This relationship seems separate from the roles, but I'll keep it for context.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets;

}
