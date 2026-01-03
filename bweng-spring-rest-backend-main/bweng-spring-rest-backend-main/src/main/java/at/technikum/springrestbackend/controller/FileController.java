package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        String objectKey = fileStorageService.upload(file, "files");
        return ResponseEntity.ok(objectKey);
    }

    @GetMapping("/**")
    public ResponseEntity<InputStreamResource> getFile(
            HttpServletRequest request
    ) {
        try {
            String objectPath = request
                    .getRequestURI()
                    .replace("/api/files/", "");

            InputStream stream = fileStorageService.download(objectPath);

            String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // default
            if (objectPath.endsWith(".jpg") || objectPath.endsWith(".jpeg")) {
                mimeType = MediaType.IMAGE_JPEG_VALUE;
            } else if (objectPath.endsWith(".png")) {
                mimeType = MediaType.IMAGE_PNG_VALUE;
            } else if (objectPath.endsWith(".gif")) {
                mimeType = MediaType.IMAGE_GIF_VALUE;
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .header("Content-Disposition", "inline; filename=\"" + objectPath + "\"")
                    .body(new InputStreamResource(stream));

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
