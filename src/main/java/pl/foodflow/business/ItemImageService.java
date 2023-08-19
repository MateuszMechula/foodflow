package pl.foodflow.business;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.foodflow.business.dao.CategoryItemDAO;
import pl.foodflow.business.dao.ItemImageDAO;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.domain.ItemImage;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ItemImageService {

    private final CategoryItemDAO categoryItemDAO;
    private final ItemImageDAO itemImageDAO;

    public void uploadImage(Long categoryId, MultipartFile imageFile) throws IOException {
        CategoryItem categoryItem = categoryItemDAO.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "CategoryItem with id: [%s] not found".formatted(categoryId)
                ));

        String imageUrl = saveImageToFileSystem(imageFile);

        ItemImage itemImage = buildItemImage(imageUrl, categoryItem);

        itemImageDAO.save(itemImage);
    }

    private String saveImageToFileSystem(MultipartFile imageFile) throws IOException {
        String uploadDir = "src/main/resources/static/uploads";
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        Files.createDirectories(filePath.getParent());

        try (OutputStream os = Files.newOutputStream(filePath)) {
            os.write(imageFile.getBytes());
        }

        return fileName;
    }

    private static ItemImage buildItemImage(String imageUrl, CategoryItem categoryItem) {
        return ItemImage.builder()
                .imageUrl(imageUrl)
                .categoryItem(categoryItem)
                .build();
    }
}
