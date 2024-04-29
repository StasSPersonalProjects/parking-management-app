package com.parkingmanagement.controller;

import com.parkingmanagement.dto.FreePlacesCount;
import com.parkingmanagement.service.AutomaticOperationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebSocketController {

    @Autowired
    AutomaticOperationsService automaticOperationsService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketController.class);

    @Scheduled(initialDelay = 25000, fixedRate = 10000)
    public void pushParkingUpdates() {
        List<FreePlacesCount> updates = automaticOperationsService.getFreeParkingPlacesOnEachFloor();
        if (updates != null) {
            LOG.debug("Pushing updated parking status to the client.");
            messagingTemplate.convertAndSend("/topic/parking-updates", updates);
        } else {
            LOG.debug("No updates found, nothing to push to the client.");
        }
    }

}
