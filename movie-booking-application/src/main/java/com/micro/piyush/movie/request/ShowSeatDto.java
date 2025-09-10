package com.micro.piyush.movie.request;


import com.micro.piyush.movie.entity.ShowSeat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeatDto {
    private Integer seatId;
    private String seatNo;
    private String seatType;
    private Integer price;
    private Boolean isAvailable;
    private Boolean isFoodContains;
    private String status;

    public ShowSeatDto(ShowSeat seat) {
        this.seatId = seat.getId();
        this.seatNo = seat.getSeatNo();
        this.seatType = seat.getSeatType() != null ? seat.getSeatType().name() : null;
        this.price = seat.getPrice();
        this.isAvailable = seat.getIsAvailable();
        this.isFoodContains = seat.getIsFoodContains();
        this.status = seat.getIsAvailable() ? "AVAILABLE" : "BOOKED";
    }
}
