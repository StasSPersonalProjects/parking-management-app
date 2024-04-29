package com.parkingmanagement.service;

import com.parkingmanagement.dto.FreePlacesCount;
import com.parkingmanagement.dto.ParkingBuildingDto;
import com.parkingmanagement.entities.ParkingPlace;
import com.parkingmanagement.repository.ParkingPlacesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParkingManagementService {

    @Autowired
    private ParkingPlacesRepository parkingPlacesRepository;
    @Autowired
    private AutomaticOperationsService automaticOperationsService;

    @Transactional
    public void addManyParkingPlaces(ParkingBuildingDto parkingBuildingDto) {
        int floor = parkingBuildingDto.getFloor();
        int places = parkingBuildingDto.getParkingPlaces();

        for (int i = 0; i < places; i++) {
            ParkingPlace parkingPlace = new ParkingPlace(floor);
            parkingPlacesRepository.save(parkingPlace);
        }
        automaticOperationsService.updateNumberOfFloors(true);
    }

    public List<ParkingPlace> getAllPlaces() {
        return parkingPlacesRepository.findAll();
    }

    public List<FreePlacesCount> getAllFreePlacesOnEachFloor() {
        return parkingPlacesRepository.findAllFreePlacesOnEachFloor();
    }

    @Transactional
    public void setNewStatus(int number) {
        if (parkingPlacesRepository.existsById(number)) {
            parkingPlacesRepository.setNewIsFreeStatus(number);
        }
    }
}
