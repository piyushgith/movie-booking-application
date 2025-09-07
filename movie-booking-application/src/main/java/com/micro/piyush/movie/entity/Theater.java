package com.micro.piyush.movie.entity;

import com.micro.piyush.movie.enums.LocationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "theater")
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "address")
    private String address;

    @Column(name = "location", nullable = false)
    @NotBlank(message = "Location is required")
    @Enumerated(EnumType.STRING)
    private LocationType location;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Theater name is required")
    private String name;

    // Relationships
    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Show> shows;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ShowSeat> showSeats;
}
