package com.parkingmanagement.controller;

import com.parkingmanagement.dto.FreePlacesCount;
import com.parkingmanagement.dto.ParkingBuildingDto;
import com.parkingmanagement.entities.ParkingPlace;
import com.parkingmanagement.service.AutomaticOperationsService;
import com.parkingmanagement.service.ParkingManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking")
public class ParkingManagementController {

    @Autowired
    private ParkingManagementService parkingManagementService;
    @Autowired
    private AutomaticOperationsService automaticOperationsService;

    private static final Logger LOG = LoggerFactory.getLogger(ParkingManagementController.class);

    @GetMapping("/full-parking-data")
    public ResponseEntity<List<FreePlacesCount>> getBuildingData() {
        LOG.debug("Received request for fetching all free parking places on each floor.");
        return ResponseEntity.ok().body(parkingManagementService.getAllFreePlacesOnEachFloor());
    }

    @PostMapping
    public void addParkingPlaces(@RequestBody ParkingBuildingDto parkingBuildingDto) {
        LOG.debug("Received request for adding {} parking places on floor number {}.",
                parkingBuildingDto.getParkingPlaces(), parkingBuildingDto.getFloor());
        parkingManagementService.addManyParkingPlaces(parkingBuildingDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ParkingPlace>> getAllParkingPlaces() {
        LOG.debug("Received request for fetching all parking places.");
        List<ParkingPlace> result = parkingManagementService.getAllPlaces();
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/set")
    public void setNewStatus(@RequestParam int number) {
        LOG.debug("Received request for changing the status of place {}.", number);
        parkingManagementService.setNewStatus(number);
    }
}
