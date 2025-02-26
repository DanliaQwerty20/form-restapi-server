package by.kochagin.teacher_restapi.dto;

import java.util.UUID;

public record StudentDTO(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String faculty,
        int course,
        UUID researchAdvisorId,
        String educationLevel,
        String educationForm,
        int yearStudy,
        String groupStudent
) {}