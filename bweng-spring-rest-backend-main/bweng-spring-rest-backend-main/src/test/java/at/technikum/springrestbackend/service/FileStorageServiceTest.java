package at.technikum.springrestbackend.service;

import io.minio.*;
import io.minio.GetObjectResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileStorageServiceTest {

    @MockitoBean
    private MinioClient minioClient;

    private FileStorageService fileStorageService;

    private final String bucketName = "test-bucket";

    @BeforeEach
    void setUp() throws Exception {
        minioClient = mock(MinioClient.class);

        // Mock bucketExists auf false, damit init() versucht bucket zu erstellen
        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(false);

        fileStorageService = new FileStorageService(minioClient, bucketName);
        fileStorageService.init();  // ruft @PostConstruct Logik auf
    }

    @Test
    void testUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", "Hello World".getBytes()
        );

        doNothing().when(minioClient).putObject(any(PutObjectArgs.class));

        String objectName = fileStorageService.upload(file, "folder");

        assertNotNull(objectName);
        assertTrue(objectName.startsWith("folder/"));
        verify(minioClient).putObject(any(PutObjectArgs.class));
    }

    @Test
    void testDownload() throws Exception {
        // Fake InputStream
        InputStream fakeStream = new ByteArrayInputStream("data".getBytes());

        // GetObjectResponse mocken
        GetObjectResponse mockResponse = mock(GetObjectResponse.class);
        when(mockResponse.readAllBytes()).thenReturn(fakeStream.readAllBytes());
        doAnswer(invocation -> {
            fakeStream.close();
            return null; // null wird benötigt, weil doAnswer ein Object zurückgibt
        }).when(mockResponse).close();

        // MinioClient.getObject mocked auf das mockResponse
        when(minioClient.getObject(any(GetObjectArgs.class))).thenReturn(mockResponse);

        InputStream result = fileStorageService.download("folder/test.txt");

        assertNotNull(result);
        verify(minioClient).getObject(any(GetObjectArgs.class));
    }

    @Test
    void testInitCreatesBucketIfNotExists() throws Exception {
        verify(minioClient).bucketExists(any(BucketExistsArgs.class));
        verify(minioClient).makeBucket(any(MakeBucketArgs.class));
    }
}
