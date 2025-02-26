package by.korchagin.form_restapi.minio;

import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;


@Component
@Slf4j
public class MinioComponent {


    private final MinioClient minioClient;

    private final String bucketName;

    public MinioComponent(MinioClient minioClient, @Value("${minio.bucketName}") String bucketName) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
    }

    public void putObject(String objectName, InputStream inputStream, long size, String extension) {
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(inputStream, size, -1).contentType(getContentTypeByExtension(extension)).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getContentTypeByExtension(String extension) {
        return switch (extension.toLowerCase()) {
            case "doc" -> "application/msword";
            case "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "pdf" -> "application/pdf";
            case "xls" -> "application/vnd.ms-excel";
            case "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "ppt" -> "application/vnd.ms-powerpoint";
            case "pptx" -> "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            default -> "application/octet-stream";
        };
    }

    public byte[] getObject(String objectName) {
        try (InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build())) {
            return stream.readAllBytes();
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPresignedObjectUrl(String objectName) {
        try {
            // Устанавливаем срок действия пре-подписанной ссылки в 1 день
            int expiryTime = 24 * 60 * 60;
            log.info("Получаем пре-подписанную ссылку");
            // Получаем пре-подписанную ссылку для объекта
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object(objectName).expiry(expiryTime, TimeUnit.SECONDS).build());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}