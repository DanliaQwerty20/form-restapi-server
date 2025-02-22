package by.korchagin.form_restapi.service;

import by.korchagin.form_restapi.dao.ResearchAdvisorDao;
import by.korchagin.form_restapi.dao.StudentDao;
import by.korchagin.form_restapi.dao.SurveyDao;
import by.korchagin.form_restapi.dto.ConferenceApplicationDTO;
import by.korchagin.form_restapi.dto.ResearchAdvisorDTO;
import by.korchagin.form_restapi.dto.SurveyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConferenceService {

    private final StudentDao studentDao;
    private final ResearchAdvisorDao researchAdvisorDao;
    private final SurveyDao surveyDao;

    @Autowired
    public ConferenceService(StudentDao studentDao, ResearchAdvisorDao researchAdvisorDao, SurveyDao surveyDao) {
        this.studentDao = studentDao;
        this.researchAdvisorDao = researchAdvisorDao;
        this.surveyDao = surveyDao;
    }

    public UUID createConferenceApplication(ConferenceApplicationDTO applicationDTO) {
        UUID advisorId = researchAdvisorDao.createResearchAdvisor(applicationDTO.researchAdvisor());
        UUID studentId = studentDao.createStudent(applicationDTO.student(),advisorId);
        return surveyDao.createSurvey(applicationDTO.survey(), studentId);
    }

    public List<SurveyDTO> getAllSurveys() {
        return surveyDao.getAllSurveys();
    }


    public List<ResearchAdvisorDTO> getAllResearchAdvisors() {
        return researchAdvisorDao.getAllResearchAdvisors();
    }
}
