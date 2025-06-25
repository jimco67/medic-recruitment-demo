package com.demo.medicrecruitment;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONObject;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;

@Getter
@RequiredArgsConstructor
public class KafkaMock {

    private final ObjectMapper objectMapper;
    private final EmbeddedKafkaBroker embeddedKafkaBroker;

    BlockingQueue<ConsumerRecord<String, String>> records;
    KafkaMessageListenerContainer<String, String> container;

    public void setUpKafka(List<String> topics) {
        Map<String, Object> configs = new HashMap<>(
                KafkaTestUtils.consumerProps("consumer", "false", embeddedKafkaBroker)
        );
        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(
                configs,
                new StringDeserializer(),
                new StringDeserializer()
        );
        ContainerProperties containerProperties = new ContainerProperties(topics.toArray(new String[] {}));
        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        records = new LinkedBlockingQueue<>();
        container.setupMessageListener((MessageListener<String, String>) records::add);
        container.start();
        ContainerTestUtils.waitForAssignment(
                container,
                Objects.requireNonNull(containerProperties.getTopics()).length * embeddedKafkaBroker.getPartitionsPerTopic()
        );
    }

    public void tearDown() {
        if (records != null) {
            records.clear();
        }

        if (container != null && container.isRunning()) {
            container.stop();
        }
    }

    public PollResult poll(long timeout) throws InterruptedException {
        var result = new PollResult();

        while (true) {
            ConsumerRecord<String, String> singleRecord = records.poll(timeout, TimeUnit.MILLISECONDS);
            if (singleRecord == null) {
                return result;
            }
            result.receivedMessages.add(
                    KafkaMessage.builder().topic(singleRecord.topic()).payload(singleRecord.value()).build()
            );
        }
    }

    public PollResult poll() throws InterruptedException {
        return poll(100);
    }

    @Builder
    private static class KafkaMessage {

        private String topic;
        private String payload;
    }

    public class PollResult {

        private final ArrayList<KafkaMessage> receivedMessages = new ArrayList<>();

        public PollResult expectTopicNotToHaveReceivedMessage(String topic) {
            assertThat(receivedMessages.stream().anyMatch(message -> message.topic.equals(topic))).isFalse();
            return this;
        }

        public PollResult expectTopicToHaveReceivedMessage(String topic) {
            assertThat(receivedMessages.stream().anyMatch(message -> message.topic.equals(topic))).isTrue();
            return this;
        }

        public PollResult expectTopicToHaveReceivedMessageOnlyOnce(String topic) {
            assertThat(receivedMessages.stream().filter(message -> message.topic.equals(topic)).count()).isOne();
            return this;
        }

        public <T> PollResult assertPayload(String topic, Class<T> clazz, Predicate<T> assertion) {
            return assertPayloadWithKafkaMessage(
                    topic,
                    message -> {
                        try {
                            return assertion.test(objectMapper.readValue(message.payload, clazz));
                        } catch (Exception e) {
                            // Failed deserialization is considered as failing assertion
                            return false;
                        }
                    }
            );
        }

        public <T> PollResult assertJSONSubPayload(
                String topic,
                String jsonKey,
                Class<T> clazz,
                Predicate<T> assertion
        ) {
            return assertPayloadWithKafkaMessage(
                    topic,
                    message -> {
                        try {
                            return assertion.test(
                                    objectMapper.readValue(new JSONObject(message.payload).get(jsonKey).toString(), clazz)
                            );
                        } catch (Exception e) {
                            // Failed deserialization is considered as failing assertion
                            return false;
                        }
                    }
            );
        }

        private PollResult assertPayloadWithKafkaMessage(String topic, Predicate<KafkaMessage> assertion) {
            assertThat(
                    receivedMessages.stream().anyMatch(message -> (message.topic.equals(topic) && assertion.test(message)))
            )
                    .isTrue();
            return this;
        }
    }
}

