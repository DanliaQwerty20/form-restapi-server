package by.kochagin.teacher_restapi.dao;

import by.kochagin.teacher_restapi.dto.SurveyDetailDTO;
import by.kochagin.teacher_restapi.dto.SurveyResultDTO;
import by.kochagin.teacher_restapi.dto.SurveyUpdateRequestDTO;

import java.util.List;
import java.util.UUID;

public interface SurveyDao {
    List<SurveyResultDTO> getSurveysWithPagination(int offset, int pageSize);

    void updateSurvey(UUID applicationId, SurveyUpdateRequestDTO updateRequest);

    void updateSurveyStatus(UUID applicationId, String s);

    SurveyDetailDTO getApplicationById(UUID applicationId);
}
