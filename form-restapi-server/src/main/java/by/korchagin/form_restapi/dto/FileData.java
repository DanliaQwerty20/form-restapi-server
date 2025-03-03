package by.korchagin.form_restapi.dto;

import java.util.UUID;

public record FileData(
        UUID applicationId,
        String fileName,
        String filePath,
        String fileType
) {}