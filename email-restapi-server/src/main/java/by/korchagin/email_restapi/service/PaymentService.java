package by.korchagin.email_restapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
/*
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final KafkaTemplate<String, OtpDto> kafkaTemplate;
    public void acceptPayment() {
        OtpDto otpDto = preparePayment();
        this.sendPushAsync(otpDto);
    }


    private void sendPushAsync(OtpDto otpDto) {
        kafkaTemplate.sendDefault(otpDto);
    }
    private static OtpDto preparePayment() {
        return new OtpDto("payment-processor","me","my-secret-code",LocalDateTime.now().plusMinutes(1));
    }
}



 */