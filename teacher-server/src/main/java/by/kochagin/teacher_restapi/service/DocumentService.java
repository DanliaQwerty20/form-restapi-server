package by.kochagin.teacher_restapi.service;

import by.kochagin.teacher_restapi.api.DocGeneratorClient;
import feign.Response;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

@Service
public class DocumentService {

    private final DocGeneratorClient docGeneratorClient;

    public DocumentService(DocGeneratorClient docGeneratorClient) {
        this.docGeneratorClient = docGeneratorClient;
    }

    public File generateDocument(Map<String, String> requestData) throws IOException {
        Response response = docGeneratorClient.generateDoc(requestData);

        if (response.status() != 200) {
            throw new RuntimeException("Ошибка генерации документа");
        }

        String outputFilename = "filled_result_" + UUID.randomUUID() + ".docx";
        Path outputPath = Path.of(outputFilename);
        Files.copy(response.body().asInputStream(), outputPath, StandardCopyOption.REPLACE_EXISTING);

        return outputPath.toFile();
    }
}