package at.technikum.springrestbackend.config;

import io.minio.*;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MinioConfigTest {

    @Test
    void minioClient_bucketDoesNotExist_makeBucketCalled() throws Exception {
        // mocks
        MinioClient minioClient = mock(MinioClient.class);
        MinioClient.Builder builder = mock(MinioClient.Builder.class);

        when(builder.endpoint(any(String.class))).thenReturn(builder);
        when(builder.credentials(any(), any())).thenReturn(builder);
        when(builder.build()).thenReturn(minioClient);

        when(minioClient.bucketExists(any())).thenReturn(false);

        try (MockedStatic<MinioClient> mockedStatic = mockStatic(MinioClient.class)) {
            mockedStatic.when(MinioClient::builder).thenReturn(builder);

            MinioConfig config = new MinioConfig();

            config.minioClient(
                    "localhost",
                    9000,
                    "key",
                    "secret",
                    false,
                    "bucket"
            );

            verify(minioClient).bucketExists(any());
            verify(minioClient).makeBucket(any());
        }
    }

    @Test
    void minioClient_bucketExists_noMakeBucket() throws Exception {
        MinioClient minioClient = mock(MinioClient.class);
        MinioClient.Builder builder = mock(MinioClient.Builder.class);

        when(builder.endpoint(any(String.class))).thenReturn(builder);
        when(builder.credentials(any(), any())).thenReturn(builder);
        when(builder.build()).thenReturn(minioClient);

        when(minioClient.bucketExists(any())).thenReturn(true);

        try (MockedStatic<MinioClient> mockedStatic = mockStatic(MinioClient.class)) {
            mockedStatic.when(MinioClient::builder).thenReturn(builder);

            new MinioConfig().minioClient(
                    "localhost", 9000, "a", "b", false, "bucket"
            );

            verify(minioClient, never()).makeBucket(any());
        }
    }

    @Test
    void minioClient_bucketExists_throwsException() throws Exception {
        MinioClient minioClient = mock(MinioClient.class);
        MinioClient.Builder builder = mock(MinioClient.Builder.class);

        when(builder.endpoint(any(String.class))).thenReturn(builder);
        when(builder.credentials(any(), any())).thenReturn(builder);
        when(builder.build()).thenReturn(minioClient);

        when(minioClient.bucketExists(any()))
                .thenThrow(new RuntimeException("boom"));

        try (MockedStatic<MinioClient> mockedStatic = mockStatic(MinioClient.class)) {
            mockedStatic.when(MinioClient::builder).thenReturn(builder);

            new MinioConfig().minioClient(
                    "localhost", 9000, "a", "b", false, "bucket"
            );
        }
    }
}
