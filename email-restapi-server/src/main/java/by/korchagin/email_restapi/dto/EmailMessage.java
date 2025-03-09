package by.korchagin.email_restapi.dto;

public record EmailMessage(
        String recipient,
        String subject,
        String body) {
}
