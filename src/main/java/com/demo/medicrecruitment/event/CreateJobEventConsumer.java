package com.demo.medicrecruitment.event;

import org.springframework.kafka.annotation.KafkaListener;

public class CreateJobEventConsumer {

    @KafkaListener(topics = "create_job_event")
    public void handleOrderEvent(CreateJobEvent createJobEvent) {
        // Process the order event, e.g., store it in the database
        System.out.println("Received Create Job Event: " + createJobEvent);
        // Implement the logic for order processing and updating inventory
    }
}
