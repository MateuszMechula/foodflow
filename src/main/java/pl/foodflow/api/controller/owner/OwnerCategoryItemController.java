package pl.foodflow.api.controller.owner;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.foodflow.api.dto.CategoryItemDTO;
import pl.foodflow.api.dto.mapper.CategoryItemMapper;
import pl.foodflow.business.CategoryItemService;
import pl.foodflow.business.MenuCategoryService;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.database.entity.CategoryItemEntity;
import pl.foodflow.infrastructure.security.user.UserService;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.foodflow.api.controller.owner.OwnerCategoryItemController.OWNER;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping(value = OWNER)
public class OwnerCategoryItemController {

    public static final String OWNER = "/owner";
    public static final String CATEGORY_ITEM = "/items";
    public static final String DELETE_CATEGORY_ITEM = "/category-items";

    private final UserService userService;
    private final CategoryItemMapper categoryItemMapper;
    private final MenuCategoryService menuCategoryService;
    private final CategoryItemService categoryItemService;

    @GetMapping(value = CATEGORY_ITEM)
    public String showCreateCategoryItemForm(Model model) {
        log.info("Displaying create category item form.");
        prepareCreateCategoryItemFormModel(model);

        return "owner_category_item";
    }

    @PostMapping(value = CATEGORY_ITEM)
    public String addCategoryItem(
            @Valid @ModelAttribute("categoryItemDTO") CategoryItemDTO categoryItemDTO,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam(value = "menuCategory", required = false) Long menuCategoryId) throws IOException {

        Owner owner = userService.getCurrentOwner();
        CategoryItem categoryItem = categoryItemMapper.map(categoryItemDTO);
        menuCategoryService.addCategoryItemToMenuCategory(menuCategoryId, owner, categoryItem, imageFile);

        log.info("Added a new Category Item: [{}]", categoryItem.getName());
        return "redirect:/owner/items";
    }

    @PostMapping(value = DELETE_CATEGORY_ITEM)
    public String deleteCategoryItem(@RequestParam Long categoryItemId) {
        categoryItemService.deleteCategoryItemById(categoryItemId);
        log.info("CategoryItem with ID: [%s] was delete".formatted(categoryItemId));
        return "redirect:/owner/items";
    }

    private void prepareCreateCategoryItemFormModel(Model model) {
        Owner owner = userService.getCurrentOwner();

        Set<MenuCategory> allCategories = getAllCategoriesFromOwner(owner);
        Map<MenuCategory, Set<CategoryItem>> menuCategoryItems = allCategories.stream()
                .collect(Collectors.toMap(
                        menuCategory -> menuCategory,
                        MenuCategory::getCategoryItems
                ));

        model.addAttribute("categoryItemDTO", new CategoryItemEntity());
        model.addAttribute("allCategories", allCategories);
        model.addAttribute("menuCategoryItems", menuCategoryItems);
    }

    private Set<MenuCategory> getAllCategoriesFromOwner(Owner owner) {
        if (owner != null && owner.getRestaurant() != null
                && owner.getRestaurant().getMenu() != null
                && owner.getRestaurant().getMenu().getMenuCategories() != null) {

            return owner.getRestaurant().getMenu().getMenuCategories();
        }
        return new HashSet<>();
    }
}
