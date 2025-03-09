package by.korchagin.email_restapi.controller;


import by.korchagin.email_restapi.kafka.KafkaProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final KafkaProducer kafkaProducer;

    public EmailController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestParam("message") String message) {
        kafkaProducer.sendMessage(message);
        return "Email notification sent successfully!";
    }
}