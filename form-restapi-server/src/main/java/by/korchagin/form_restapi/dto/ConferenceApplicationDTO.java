package by.korchagin.form_restapi.dto;

public record ConferenceApplicationDTO(
        StudentDTO student,
        ResearchAdvisorDTO researchAdvisor,
        SurveyDTO survey
) {}
