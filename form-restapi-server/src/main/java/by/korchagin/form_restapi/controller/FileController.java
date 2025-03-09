package by.korchagin.form_restapi.controller;


import by.korchagin.form_restapi.api.EmailClient;
import by.korchagin.form_restapi.dto.EmailRequest;
import by.korchagin.form_restapi.dto.FileData;
import by.korchagin.form_restapi.service.FileService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;
    private final EmailClient emailClient;

    public FileController(FileService fileService, EmailClient emailClient) {
        this.fileService = fileService;
        this.emailClient = emailClient;
    }

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadFiles(
            @RequestParam("applicationId") UUID applicationId,
            @RequestParam("signedFile") MultipartFile signedFile,
            @RequestParam("additionalFile") MultipartFile additionalFile
    ) {
        fileService.uploadFiles(applicationId, signedFile, additionalFile);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<List<Map<String, String>>> getFilesByApplicationId(@PathVariable UUID applicationId) {
        List<FileData> files = fileService.getFilesByApplicationId(applicationId);
        List<Map<String, String>> fileUrls = new ArrayList<>();

        for (FileData file : files) {
            String presignedUrl = fileService.getPresignedUrl(file.filePath());
            Map<String, String> fileInfo = new HashMap<>();
            fileInfo.put("fileName", file.fileName());
            fileInfo.put("url", presignedUrl);
            fileInfo.put("type", file.fileType());
            fileUrls.add(fileInfo);
        }

        return ResponseEntity.ok(fileUrls);
    }

    @GetMapping("/download/{filePath}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filePath) {
        byte[] fileContent = fileService.downloadFile(filePath);
        String fileName = fileService.getFileNameByPath(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }


    @GetMapping("/send")
    public ResponseEntity<String> sendTestEmail(@RequestParam String recipient) {
        emailClient.sendEmail(new EmailRequest(
                recipient,
                "Тестовое письмо",
                "Это тестовое письмо для проверки работы email-сервиса."
        ));
        return ResponseEntity.ok("Тестовое письмо отправлено на " + recipient);
    }
}