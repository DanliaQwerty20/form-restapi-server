package by.korchagin.form_restapi.dto;

import java.time.LocalDate;

public record SurveyDTO(
        String topicTitle,
        String topicDescription,
        LocalDate conferenceDate
) {}
