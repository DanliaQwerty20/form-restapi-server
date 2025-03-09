package by.korchagin.form_restapi.dto;

public record EmailRequest(
        String recipient,
        String subject,
        String body
) {
}
