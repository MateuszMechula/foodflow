package pl.foodflow.integration.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.foodflow.business.MenuCategoryService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MenuCategoryServiceIntegrationTest {

    @Autowired
    private MenuCategoryService menuCategoryService;
    private MultipartFile imageFile;
    private String uploadDir;

    @BeforeEach
    public void setUp() {
        uploadDir = "src/main/resources/static/images";
        imageFile = createTestImageFile();
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(uploadDir, imageFile.getOriginalFilename()));
    }

    @Test
    public void shouldUploadImage() throws IOException {
        String uploadedFileName = menuCategoryService.uploadImage(imageFile);

        Path filePath = Paths.get(uploadDir, uploadedFileName);
        boolean fileExists = Files.exists(filePath);
        assertTrue(fileExists);
    }

    private MultipartFile createTestImageFile() {
        byte[] fileContent = "SomeContent".getBytes();
        return new MockMultipartFile("testFile.jpg", "testFile.jpg", "image/jpeg",
                fileContent);
    }
}

