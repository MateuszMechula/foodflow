package pl.foodflow.api.controller.rest.owner;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import pl.foodflow.business.OwnerService;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.domain.Owner;

import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = OwnerCategoryItemRestController.CATEGORY_ITEMS)
@Tag(name = "owner categoryItem")
public class OwnerCategoryItemRestController {
    public static final String CATEGORY_ITEMS = "/api/v1/owner/category-items";
    public static final String MENU_CATEGORY_ID = "/{menuCategoryId}";
    public static final String CATEGORY_ITEM_ID = "/{categoryItemId}";
    public static final String OWNER_ID = "/{ownerId}";

    private final OwnerService ownerService;
    private final CategoryItemMapper categoryItemMapper;
    private final MenuCategoryService menuCategoryService;
    private final CategoryItemService categoryItemService;

    @Operation(summary = "Find categoryItem")
    @GetMapping(value = CATEGORY_ITEM_ID)
    public ResponseEntity<CategoryItemDTO> findCategoryItem(@PathVariable Long categoryItemId) {
        CategoryItem categoryItem = categoryItemService.getCategoryItemById(categoryItemId);
        CategoryItemDTO categoryItemDTO = categoryItemMapper.mapToDTO(categoryItem);
        return ResponseEntity.status(HttpStatus.OK).body(categoryItemDTO);
    }

    @Operation(summary = "Add categoryItem")
    @PostMapping(value = MENU_CATEGORY_ID + OWNER_ID)
    public ResponseEntity<Void> addCategoryItem(
            @PathVariable Long menuCategoryId,
            @PathVariable Long ownerId,
            @RequestParam("imageFile") MultipartFile imageFile,
            @Valid @RequestBody CategoryItemDTO categoryItemDTO) throws IOException {

        Owner owner = ownerService.findOwnerById(ownerId);
        CategoryItem categoryItem = categoryItemMapper.map(categoryItemDTO);
        menuCategoryService.addCategoryItemToMenuCategory(menuCategoryId, owner, categoryItem, imageFile);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Delete categoryItem")
    @DeleteMapping(value = CATEGORY_ITEM_ID)
    public ResponseEntity<Void> deleteCategoryItem(@PathVariable Long categoryItemId) {
        categoryItemService.deleteCategoryItemById(categoryItemId);
        log.info("CategoryItem with ID: [%s] was deleted".formatted(categoryItemId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
