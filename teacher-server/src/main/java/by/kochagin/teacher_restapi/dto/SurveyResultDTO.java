package by.kochagin.teacher_restapi.dto;

import java.time.LocalDate;
import java.util.UUID;

public record SurveyResultDTO(
        UUID uuid,
        String topicTitle,
        String studentFullName,
        String advisorFullName,
        LocalDate conferenceDate,
        String status
) {}
