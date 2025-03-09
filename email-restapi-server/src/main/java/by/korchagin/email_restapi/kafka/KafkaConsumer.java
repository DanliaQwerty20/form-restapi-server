package by.korchagin.email_restapi.kafka;

import by.korchagin.email_restapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final EmailService emailService;


    private final String[] recipients = new String[]{"danlia2000789@gmail.com"};

    @Autowired
    public KafkaConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "email_topic", groupId = "group_id")
    public void consume(String message) {
        for (String recipient : recipients) {
            emailService.sendEmail(recipient, "Notification", message);
        }
    }
}