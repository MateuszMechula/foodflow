package pl.foodflow.api.controller.rest.owner;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.foodflow.api.dto.CategoryItemDTO;
import pl.foodflow.api.dto.mapper.CategoryItemMapper;
import pl.foodflow.business.CategoryItemService;
import pl.foodflow.business.MenuCategoryService;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.security.user.UserService;

import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = OwnerCategoryItemRestController.CATEGORY_ITEMS)
public class OwnerCategoryItemRestController {
    public static final String CATEGORY_ITEMS = "/owner/api/v1/category-items";
    public static final String MENU_CATEGORY_ID = "/{menuCategoryId}";
    public static final String CATEGORY_ITEM_ID = "/{categoryItemId}";

    private final UserService userService;
    private final CategoryItemMapper categoryItemMapper;
    private final MenuCategoryService menuCategoryService;
    private final CategoryItemService categoryItemService;

    @PostMapping(value = MENU_CATEGORY_ID)
    public ResponseEntity<Void> addCategoryItem(
            @PathVariable Long menuCategoryId,
            @RequestParam("imageFile") MultipartFile imageFile,
            @Valid @RequestBody CategoryItemDTO categoryItemDTO) throws IOException {

        Owner owner = userService.getCurrentOwner();
        CategoryItem categoryItem = categoryItemMapper.map(categoryItemDTO);
        menuCategoryService.addCategoryItemToMenuCategory(menuCategoryId, owner, categoryItem, imageFile);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(CATEGORY_ITEM_ID)
    public ResponseEntity<Void> deleteCategoryItem(@PathVariable Long categoryItemId) {
        categoryItemService.deleteCategoryItemById(categoryItemId);
        log.info("CategoryItem with ID: [%s] was deleted".formatted(categoryItemId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
