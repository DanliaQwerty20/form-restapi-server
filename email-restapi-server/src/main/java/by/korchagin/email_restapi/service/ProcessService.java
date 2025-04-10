package by.korchagin.email_restapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
/*
@Slf4j
@Service
public class ProcessService {
    private final EmailService emailService;
    private final PaymentService paymentService;

    public ProcessService(EmailService emailService, PaymentService paymentService) {
        this.emailService = emailService;
        this.paymentService = paymentService;
    }

    @Scheduled(fixedDelay = 5000)
    public void sendProcessMessage(String message) {
        for (int i = 0; i < 10; i++) {

            paymentService.acceptPayment();

            //emailService.sendEmail("message");
        }
        log.info("sent new batch");
    }
}


 */