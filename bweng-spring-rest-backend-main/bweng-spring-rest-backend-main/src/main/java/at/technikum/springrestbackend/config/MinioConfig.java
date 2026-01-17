package at.technikum.springrestbackend.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.UploadObjectArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient(
            @Value("${minio.host}") String host,
            @Value("${minio.port}") int port,
            @Value("${minio.access-key}") String accessKey,
            @Value("${minio.secret-key}") String secretKey,
            @Value("${minio.secure}") boolean secure,
            @Value("${minio.bucket}") String bucketName
    ) {

        String endpoint = (secure ? "https://" : "http://") + host + ":" + port;

        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();

        try {
            // Bucket prüfen / erstellen
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                System.out.println("Bucket created: " + bucketName);
            }

            // Alle Dateien im Ordner hochladen
            Path folder = Paths.get("src/main/resources/static/images/");
            if (Files.exists(folder) && Files.isDirectory(folder)) {
                try (Stream<Path> files = Files.list(folder)) {
                    files.filter(Files::isRegularFile)
                            .forEach(path -> uploadFile(minioClient, bucketName, path));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return minioClient;
    }

    private void uploadFile(MinioClient minioClient, String bucketName, Path path) {
        try {
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(path.getFileName().toString())
                            .filename(path.toString())
                            .build()
            );
            System.out.println("Uploaded " + path.getFileName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
