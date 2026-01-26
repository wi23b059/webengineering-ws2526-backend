package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.service.FileStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import jakarta.servlet.http.HttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileControllerTest {

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private FileController fileController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpload() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "Hello World".getBytes()
        );

        when(fileStorageService.upload(file, "files")).thenReturn("files/test.txt");

        ResponseEntity<String> response = fileController.upload(file);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("files/test.txt", response.getBody());
        verify(fileStorageService).upload(file, "files");
    }

    @Test
    void testGetFile() throws Exception {
        String fileName = "test.txt";
        InputStream mockStream = new ByteArrayInputStream("Hello World".getBytes());

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/files/" + fileName);
        when(fileStorageService.download(fileName)).thenReturn(mockStream);

        ResponseEntity<InputStreamResource> response = fileController.getFile(request);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        verify(fileStorageService).download(fileName);
    }

    @Test
    void testGetFileNotFound() throws Exception {
        String fileName = "missing.txt";

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/files/" + fileName);
        when(fileStorageService.download(fileName)).thenThrow(new RuntimeException("Not found"));

        ResponseEntity<InputStreamResource> response = fileController.getFile(request);

        assertEquals(404, response.getStatusCode().value());
        verify(fileStorageService).download(fileName);
    }
}
