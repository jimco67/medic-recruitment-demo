package com.demo.medicrecruitment.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CreateJobEventProducer {

    private static final String TOPIC = "create_job_event";

    @Autowired
    private KafkaTemplate<String, CreateJobEvent> kafkaTemplate;

    public void sendOrderEvent(CreateJobEvent createJobEvent) {
        kafkaTemplate.send(TOPIC, createJobEvent);
    }
}
