package by.korchagin.form_restapi.dao;

import by.korchagin.form_restapi.dto.FileData;

import java.util.List;
import java.util.UUID;

public interface FileDao {
    void saveFile(FileData fileData);

    List<FileData> getFilesByApplicationId(UUID applicationId);

    String getFileNameByPath(String filePath);
}