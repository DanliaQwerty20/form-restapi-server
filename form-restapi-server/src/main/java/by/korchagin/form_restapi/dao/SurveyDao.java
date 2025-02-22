package by.korchagin.form_restapi.dao;

import by.korchagin.form_restapi.dto.SurveyDTO;

import java.util.List;
import java.util.UUID;

public interface SurveyDao {
    UUID createSurvey(SurveyDTO survey, UUID studentId);

    List<SurveyDTO> getAllSurveys();
}
