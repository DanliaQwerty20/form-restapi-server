package by.korchagin.form_restapi.dao;

import by.korchagin.form_restapi.dto.ResearchAdvisorDTO;

import java.util.List;
import java.util.UUID;

public interface ResearchAdvisorDao {
    UUID createResearchAdvisor(ResearchAdvisorDTO researchAdvisor);

    List<ResearchAdvisorDTO> getAllResearchAdvisors();
}
