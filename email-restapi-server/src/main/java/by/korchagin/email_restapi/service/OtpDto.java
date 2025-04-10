package by.korchagin.email_restapi.service;

import java.time.LocalDateTime;

public record OtpDto(
        String sender,
        String userId,
        String code,
        LocalDateTime expireTime
) {
}
