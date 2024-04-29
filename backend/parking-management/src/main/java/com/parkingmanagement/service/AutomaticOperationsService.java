package com.parkingmanagement.service;

import com.parkingmanagement.dto.FreePlacesCount;
import com.parkingmanagement.repository.ParkingPlacesRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutomaticOperationsService {

    @Autowired
    private ParkingPlacesRepository parkingPlacesRepository;

    @Getter
    private int currentNumberOfFloors;
    @Getter
    private List<FreePlacesCount> freeParkingPlacesOnEachFloor;

    private static final Logger LOG = LoggerFactory.getLogger(AutomaticOperationsService.class);

    @PostConstruct
    public void initFloors() {
        this.currentNumberOfFloors = 0;
        LOG.debug("Initiated floors: {}", this.currentNumberOfFloors);
    }

    public void updateNumberOfFloors(boolean isIncreaseRequest) {
        if (isIncreaseRequest) {
            this.currentNumberOfFloors++;
        } else {
            this.currentNumberOfFloors--;
        }
        LOG.debug("Current number of floors: {}", this.currentNumberOfFloors);
    }

    @Scheduled(initialDelay = 20000, fixedRate = 10000)
    public void updateParkingPlacesStatusOnEachFloor() {
        LOG.debug("Trying to update data of free parking places on each floor...");
        if (currentNumberOfFloors > 0) {
            LOG.debug("Current number of floors is larger than 0, performing update...");
            freeParkingPlacesOnEachFloor = parkingPlacesRepository.findAllFreePlacesOnEachFloor();
            for (int i = 0; i < currentNumberOfFloors; i++) {
                LOG.debug("On floor number {}: {} free parking places.",
                        freeParkingPlacesOnEachFloor.get(i).getFloor(),
                        freeParkingPlacesOnEachFloor.get(i).getFreePlacesOnFloor());
            }
        } else {
            LOG.debug("No parking floors located.");
        }
    }
}
