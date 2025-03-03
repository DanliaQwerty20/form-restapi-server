package by.korchagin.form_restapi.service;

import by.korchagin.form_restapi.dao.FileDao;
import by.korchagin.form_restapi.dto.FileData;
import by.korchagin.form_restapi.minio.MinioComponent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileService {

    private final FileDao fileRepository;
    private final MinioComponent minioComponent;

    public FileService(FileDao fileRepository, MinioComponent minioComponent) {
        this.fileRepository = fileRepository;
        this.minioComponent = minioComponent;
    }

    public void uploadFiles(UUID applicationId, MultipartFile signedFile, MultipartFile additionalFile) {
        try {
            String signedFilePath = saveFileToMinio(signedFile);
            String additionalFilePath = saveFileToMinio(additionalFile);

            fileRepository.saveFile(new FileData(applicationId, signedFile.getOriginalFilename(), signedFilePath, "signed"));
            fileRepository.saveFile(new FileData(applicationId, additionalFile.getOriginalFilename(), additionalFilePath, "additional"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при загрузке файлов в MinIO", e);
        }
    }

    private String saveFileToMinio(MultipartFile file) throws IOException {
        String objectName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        minioComponent.putObject(objectName, file.getInputStream(), file.getSize(), getFileExtension(Objects.requireNonNull(file.getOriginalFilename())));
        return objectName;
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public List<FileData> getFilesByApplicationId(UUID applicationId) {
        return fileRepository.getFilesByApplicationId(applicationId);
    }

    public String getPresignedUrl(String filePath) {
        return minioComponent.getPresignedObjectUrl(filePath);
    }

    public byte[] downloadFile(String filePath) {
        return minioComponent.getObject(filePath);
    }

    public String getFileNameByPath(String filePath) {
        return fileRepository.getFileNameByPath(filePath);
    }
}