package by.korchagin.form_restapi.dto;

import java.util.UUID;

public record StudentDTO(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String faculty,
        int course,
        UUID researchAdvisorId,
        String educationLevel,
        String educationForm
) {}