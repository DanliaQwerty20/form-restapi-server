package by.korchagin.form_restapi.dao;

import java.util.UUID;

public interface FileDao {
    void saveFile(UUID applicationId, String fileName, String filePath, String fileType);
}