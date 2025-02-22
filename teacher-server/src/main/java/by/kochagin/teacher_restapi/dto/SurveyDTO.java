package by.kochagin.teacher_restapi.dto;

import java.time.LocalDate;
import java.util.UUID;

public record SurveyDTO(
        UUID id,
        String topicTitle,
        String topicDescription,
        LocalDate conferenceDate,
        String status,
        String conferenceRoom
) {}