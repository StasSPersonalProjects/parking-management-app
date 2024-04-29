package com.randomparkingdatagenerator.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class RandomDataGenerator implements CommandLineRunner {

    @Autowired
    WebClient webClientParkingManagement;

    @Override
    public void run(String... args) throws Exception {
        generateAndSendRandomData();
    }

    private void generateAndSendRandomData() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            int randomNumber = generate();
            System.out.println(randomNumber);
            webClientParkingManagement.patch()
                    .uri("/set?number=" + randomNumber)
                    .bodyValue(randomNumber)
                    .retrieve()
                    .toBodilessEntity()
                    .subscribe();
        }, 0, 10000, TimeUnit.MILLISECONDS);
    }

    private int generate() {
        return (int) (Math.random() * 200);
    }
}
