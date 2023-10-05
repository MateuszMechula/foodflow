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
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.security.user.UserService;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = OwnerMenuCategoryRestController.MENU_CATEGORIES)
public class OwnerMenuCategoryRestController {
    public static final String MENU_CATEGORIES = "/owner/api/v1/menu-categories";
    public static final String MENU_CATEGORY_ID = "/{menuCategoryId}";
    private final MenuService menuService;
    private final UserService userService;
    private final MenuCategoryMapper menuCategoryMapper;
    private final MenuCategoryService menuCategoryService;

    @PostMapping
    public ResponseEntity<Void> addMenuCategory(
            @Valid @RequestBody MenuCategoryDTO menuCategoryDTO) {
        Owner owner = userService.getCurrentOwner();
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
