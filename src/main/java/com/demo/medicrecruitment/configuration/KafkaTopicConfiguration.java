package com.demo.medicrecruitment.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfiguration {
    @Bean
    public NewTopic createTopic() {
        return new NewTopic("create_topic", 1, (short) 1);
    }
}
