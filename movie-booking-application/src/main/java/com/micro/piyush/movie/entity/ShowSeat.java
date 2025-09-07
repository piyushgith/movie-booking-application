package com.micro.piyush.movie.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.micro.piyush.movie.enums.SeatType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "show_seat")
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "is_available", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isAvailable = true;

    @Column(name = "is_food_contains")
    private Boolean isFoodContains;

    @Column(name = "price", nullable = false)
    @Min(value = 1, message = "Price must be greater than 0")
    private Integer price;

    @Column(name = "seat_no", nullable = false, length = 10)
    @NotBlank(message = "Seat number is required")
    @Size(max = 10, message = "Seat number cannot exceed 10 characters")
    private String seatNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "seat_type")
    private SeatType seatType;

    // Foreign Keys
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id", nullable = false)
    @NotNull(message = "Theater is required")
    @JsonBackReference
    private Theater theater;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    @NotNull(message = "Show is required")
    @JsonBackReference
    private Show show;

    // Relationships
    @OneToMany(mappedBy = "showSeat", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TicketSeat> ticketSeats;

}
