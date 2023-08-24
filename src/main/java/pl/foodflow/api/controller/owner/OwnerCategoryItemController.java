package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.foodflow.api.dto.CategoryItemDTO;
import pl.foodflow.api.dto.mapper.CategoryItemMapper;
import pl.foodflow.business.CategoryItemService;
import pl.foodflow.business.OwnerService;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.database.entity.CategoryItemEntity;
import pl.foodflow.infrastructure.security.UserService;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static pl.foodflow.api.controller.owner.OwnerCategoryItemController.OWNER;

@Controller
@AllArgsConstructor
@RequestMapping(value = OWNER)
public class OwnerCategoryItemController {
    public static final String OWNER = "/owner";
    public static final String CATEGORY_ITEM = "/items";

    private final CategoryItemService categoryItemService;
    private final UserService userService;
    private final OwnerService ownerService;
    private final CategoryItemMapper categoryItemMapper;

    @GetMapping(value = CATEGORY_ITEM)
    public String createCategoryItemForm(
            Model model,
            Authentication authentication) {
        String username = authentication.getName();
        int userId = userService.findByUserName(username).getUserId();
        Owner owner = ownerService.findByUserIdWithMenuAndCategoryAndItems(userId);

        Set<MenuCategory> allCategories = owner != null &&
                owner.getRestaurant() != null &&
                owner.getRestaurant().getMenu() != null &&
                owner.getRestaurant().getMenu().getMenuCategories() != null
                ? owner.getRestaurant().getMenu().getMenuCategories()
                : new HashSet<>();

        model.addAttribute("categoryItemDTO", new CategoryItemEntity());
        model.addAttribute("allCategories", allCategories);
        return "owner_category_item";
    }

    @PostMapping(value = CATEGORY_ITEM)
    public String addCategoryItem(
            @ModelAttribute("categoryItemDTO") CategoryItemDTO categoryItemDTO,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("menuCategory") Long menuCategoryId,
            Authentication authentication
    ) throws IOException {
        String username = authentication.getName();
        int userId = userService.findByUserName(username).getUserId();
        Owner owner = ownerService.findByUserId(userId);

        CategoryItem categoryItem = categoryItemMapper.map(categoryItemDTO);

        categoryItemService.addItemToMenuCategory(menuCategoryId, owner, categoryItem, imageFile);

        return "redirect:" + OWNER;
    }
}
