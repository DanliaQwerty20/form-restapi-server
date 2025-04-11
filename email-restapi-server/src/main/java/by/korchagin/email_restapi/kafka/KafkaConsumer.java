package by.korchagin.email_restapi.kafka;

import by.korchagin.email_restapi.conf.KafkaTemplateConfiguration;
import by.korchagin.email_restapi.dto.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;

    private final List<String> recipients = List.of("danlia2003zx@gmail.com", "aldar.anchaev@gmail.com");

    @KafkaListener(topics = KafkaTemplateConfiguration.EMAIL_TOPIC, groupId = "group_id")
    public void consume(Message message) {
        log.info("Received message from Kafka: {}", message);

        for (String to : recipients) {
            try {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom(from);
                mailMessage.setTo(to);
                mailMessage.setSubject("Kafka Email Notification");
                mailMessage.setText(message.message());

                javaMailSender.send(mailMessage);
                log.info("Email sent successfully to: {}", to);
            } catch (Exception e) {
                log.error("Failed to send email to {}: {}", to, e.getMessage());
            }
        }
    }
}
