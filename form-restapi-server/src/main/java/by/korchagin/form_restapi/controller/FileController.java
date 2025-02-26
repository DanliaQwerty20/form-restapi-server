package by.korchagin.form_restapi.controller;


import by.korchagin.form_restapi.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
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
}