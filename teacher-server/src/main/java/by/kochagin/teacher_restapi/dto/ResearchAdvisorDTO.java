package by.kochagin.teacher_restapi.dto;

import java.util.UUID;

public record ResearchAdvisorDTO(
        UUID id,
        String firstName,
        String lastName,
        String academicTitle,
        String department,
        String university,
        String email,
        String phoneNumber
) {}