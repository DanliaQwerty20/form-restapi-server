package by.korchagin.form_restapi.controller;

import by.korchagin.form_restapi.dto.ConferenceApplicationDTO;
import by.korchagin.form_restapi.dto.ResearchAdvisorDTO;
import by.korchagin.form_restapi.dto.SurveyDTO;
import by.korchagin.form_restapi.service.ConferenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/conference")
public class SurveyController {

    private final ConferenceService conferenceService;

    public SurveyController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> saveConferenceRequest(@RequestBody ConferenceApplicationDTO conferenceApplicationDTO) {
        conferenceService.createConferenceApplication(conferenceApplicationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get")
    public ResponseEntity<List<SurveyDTO>> saveConferenceRequest() {
        return ResponseEntity.ofNullable(conferenceService.getAllSurveys());
    }

    @GetMapping("/advisors")
    public ResponseEntity<List<ResearchAdvisorDTO>> getAllResearchAdvisors() {
        return ResponseEntity.ok(conferenceService.getAllResearchAdvisors());
    }
}