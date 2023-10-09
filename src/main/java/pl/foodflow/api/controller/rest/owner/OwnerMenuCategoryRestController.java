package pl.foodflow.api.controller.rest.owner;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.foodflow.api.dto.MenuCategoryDTO;
import pl.foodflow.api.dto.mapper.MenuCategoryMapper;
import pl.foodflow.business.MenuCategoryService;
import pl.foodflow.business.MenuService;
import pl.foodflow.business.OwnerService;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Owner;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = OwnerMenuCategoryRestController.MENU_CATEGORIES)
public class OwnerMenuCategoryRestController {
    public static final String MENU_CATEGORIES = "/api/v1/owner/menu-categories";
    public static final String MENU_CATEGORY_ID = "/{menuCategoryId}";
    public static final String OWNER_ID = "/{ownerId}";

    private final MenuService menuService;
    private final OwnerService ownerService;
    private final MenuCategoryMapper menuCategoryMapper;
    private final MenuCategoryService menuCategoryService;

    @GetMapping(value = MENU_CATEGORY_ID)
    public ResponseEntity<MenuCategory> getMenuCategoryById(@PathVariable Long menuCategoryId) {
        MenuCategory menuCategory = menuCategoryService.findMenuCategoryById(menuCategoryId);
        return ResponseEntity.status(HttpStatus.OK).body(menuCategory);
    }

    @PostMapping(value = OWNER_ID)
    public ResponseEntity<Void> addMenuCategory(
            @Valid @RequestBody MenuCategoryDTO menuCategoryDTO,
            @PathVariable Long ownerId) {

        Owner owner = ownerService.findOwnerById(ownerId);
        MenuCategory menu = menuCategoryMapper.map(menuCategoryDTO);
        menuService.addMenuCategoryToMenu(owner, menu);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(value = MENU_CATEGORY_ID)
    public ResponseEntity<Void> deleteMenuCategory(@PathVariable Long menuCategoryId) {
        menuCategoryService.deleteMenuCategoryById(menuCategoryId);
        log.info("MenuCategory with ID: [%s] was delete".formatted(menuCategoryId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}