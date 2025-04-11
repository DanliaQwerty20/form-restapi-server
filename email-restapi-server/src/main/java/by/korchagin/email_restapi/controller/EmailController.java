package by.korchagin.email_restapi.controller;


import by.korchagin.email_restapi.kafka.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/email")
public class EmailController {

    private final KafkaProducer kafkaProducer;

    public EmailController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestParam("message") String message) throws ExecutionException, InterruptedException {
        if (message == null || message.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Message cannot be empty");
        }

        log.info("Sending message to Kafka: {}", message);
        kafkaProducer.sendMessage(message);
        return ResponseEntity.ok("Email notification sent successfully!");
    }

}