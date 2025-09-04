package com.micro.piyush.movie.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "show_seats")
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Boolean isAvailable = true;

    private Boolean isFoodContains;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "theater_seat_id", nullable = false)
    private TheaterSeat theaterSeat;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

}
