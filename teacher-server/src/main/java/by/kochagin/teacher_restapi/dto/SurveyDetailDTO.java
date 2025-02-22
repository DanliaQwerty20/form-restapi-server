package by.kochagin.teacher_restapi.dto;

import java.util.UUID;

public record SurveyDetailDTO(
        UUID id,
        StudentDTO student,
        ResearchAdvisorDTO researchAdvisor,
        SurveyDTO survey
) {
}