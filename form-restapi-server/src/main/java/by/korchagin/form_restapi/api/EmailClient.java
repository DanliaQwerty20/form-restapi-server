package by.korchagin.form_restapi.api;

import by.korchagin.form_restapi.dto.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "email-service", url = "http://localhost:8004")
public interface EmailClient {

    @PostMapping("/email/send")
    void sendEmail(@RequestBody EmailRequest emailRequest);
}