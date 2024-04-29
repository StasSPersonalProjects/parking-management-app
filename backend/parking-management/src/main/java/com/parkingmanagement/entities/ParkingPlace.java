package com.parkingmanagement.entities;

import com.parkingmanagement.dto.ParkingPlaceDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parking_places")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int number;
    private int floor;
    @Column(name = "is_free")
    private boolean isFree;

    public ParkingPlace(int floor) {
        this.floor = floor;
        this.isFree = true;
    }

    public static ParkingPlace of(ParkingPlaceDto parkingPlaceDto) {
        return new ParkingPlace(parkingPlaceDto.getFloor());
    }

}
