package by.korchagin.email_restapi.kafka;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class KafkaProducer {

    private static final String TOPIC = "email_topic";

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) throws ExecutionException, InterruptedException {
        this.kafkaTemplate.sendDefault(TOPIC, message).get();
        log.info("Message sent to Kafka: {}", message);
    }
}

