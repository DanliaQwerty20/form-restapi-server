package by.korchagin.email_restapi.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/*
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final ProcessService processService;


    @PostMapping("/send")
    public String sendEmail(@RequestParam("message") String message) {
        processService.sendProcessMessage(message);
        return "Email notification sent successfully!";
    }

}


 */