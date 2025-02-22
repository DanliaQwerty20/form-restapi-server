package by.kochagin.teacher_restapi.controller;

import by.kochagin.teacher_restapi.dto.SurveyDetailDTO;
import by.kochagin.teacher_restapi.dto.SurveyResultDTO;
import by.kochagin.teacher_restapi.dto.SurveyUpdateRequestDTO;
import by.kochagin.teacher_restapi.service.ApplicationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/applications")
public class ApplicationsController {
    private final ApplicationsService applicationsService;

    @Autowired
    public ApplicationsController(ApplicationsService applicationsService) {
        this.applicationsService = applicationsService;
    }

    @GetMapping
    public ResponseEntity<List<SurveyResultDTO>> getSurveys(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ofNullable(applicationsService.getSurveys(page, pageSize));
    }

    @PutMapping("/{applicationId}")
    public ResponseEntity<Void> updateApplication(@PathVariable UUID applicationId,
                                                  @RequestBody SurveyUpdateRequestDTO updateRequest) {
        applicationsService.updateApplication(applicationId, updateRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<SurveyDetailDTO> getApplicationById(@PathVariable UUID applicationId) {
        return ResponseEntity.ok(applicationsService.getApplicationById(applicationId));
    }

    @PutMapping("/{applicationId}/status")
    public ResponseEntity<Void> updateApplicationStatus(@PathVariable UUID applicationId, @RequestBody String status) {
        applicationsService.updateApplicationStatus(applicationId, status);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{applicationId}/approve")
    public ResponseEntity<Void> approveApplication(@PathVariable UUID applicationId) {
        applicationsService.approveApplication(applicationId);
        return ResponseEntity.noContent().build();
    }
}
