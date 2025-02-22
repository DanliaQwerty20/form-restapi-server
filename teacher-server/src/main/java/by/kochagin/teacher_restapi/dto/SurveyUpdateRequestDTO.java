package by.kochagin.teacher_restapi.dto;

public record SurveyUpdateRequestDTO(
        StudentDTO student,
        ResearchAdvisorDTO researchAdvisor,
        SurveyDTO survey
) {
}
