package by.kochagin.teacher_restapi.service;

import by.kochagin.teacher_restapi.dao.SurveyDao;
import by.kochagin.teacher_restapi.dto.SurveyDetailDTO;
import by.kochagin.teacher_restapi.dto.SurveyResultDTO;
import by.kochagin.teacher_restapi.dto.SurveyUpdateRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ApplicationsService {
    private final SurveyDao surveyDao;

    @Autowired
    public ApplicationsService(SurveyDao surveyDao) {
        this.surveyDao = surveyDao;
    }

    public List<SurveyResultDTO> getSurveys(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return surveyDao.getSurveysWithPagination(offset, pageSize);
    }

    public void updateApplication(UUID applicationId, SurveyUpdateRequestDTO updateRequest) {
        surveyDao.updateSurvey(applicationId, updateRequest);
    }

    public void approveApplication(UUID applicationId) {
        surveyDao.updateSurveyStatus(applicationId, "Утверждено");
    }

    public void updateApplicationStatus(UUID applicationId, String status) {
        surveyDao.updateSurveyStatus(applicationId, status);
    }

    public SurveyDetailDTO getApplicationById(UUID applicationId) {
        return surveyDao.getApplicationById(applicationId);
    }
}
