package by.korchagin.form_restapi.service;

import by.korchagin.form_restapi.dao.FileDao;
import by.korchagin.form_restapi.minio.MinioComponent;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
            // Сохраняем файлы в MinIO
            String signedFilePath = saveFileToMinio(signedFile);
            String additionalFilePath = saveFileToMinio(additionalFile);

            // Сохранение информации о файлах в базе данных
            fileRepository.saveFile(applicationId, signedFile.getOriginalFilename(), signedFilePath, "signed");
            fileRepository.saveFile(applicationId, additionalFile.getOriginalFilename(), additionalFilePath, "additional");
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
}