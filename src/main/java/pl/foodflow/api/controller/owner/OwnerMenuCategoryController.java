package pl.foodflow.api.controller.owner;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.foodflow.api.dto.MenuCategoryDTO;
import pl.foodflow.api.dto.mapper.MenuCategoryMapper;
import pl.foodflow.business.MenuCategoryService;
import pl.foodflow.business.MenuService;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.security.user.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pl.foodflow.api.controller.owner.OwnerMenuCategoryController.OWNER;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping(value = OWNER)
public class OwnerMenuCategoryController {
    public static final String OWNER = "/owner";
    public static final String CATEGORY = "/category";
    public static final String DELETE_CATEGORY = "/delete-category";

    private final MenuService menuService;
    private final UserService userService;
    private final MenuCategoryMapper menuCategoryMapper;
    private final MenuCategoryService menuCategoryService;

    @GetMapping(value = CATEGORY)
    public ModelAndView menuCategorySection() {
        Owner owner = userService.getCurrentOwner();
        MenuCategoryDTO categoryDTO = MenuCategoryDTO.buildDefault();

        Map<String, Object> model = new HashMap<>();
        model.put("categoryDTO", categoryDTO);

        if (owner != null && owner.getRestaurant() != null && owner.getRestaurant().getMenu() != null) {
            List<MenuCategory> allMenuCategories = menuCategoryService.findAllCategoriesByMenuId(owner.getRestaurant().getMenu().getMenuId());
            if (allMenuCategories != null) {
                model.put("allMenuCategories", allMenuCategories);
            }
        }
        return new ModelAndView("owner_menu_category", model);
    }

    @PostMapping(value = CATEGORY)
    public String addMenuCategory(
            @Valid @ModelAttribute("categoryDTO") MenuCategoryDTO menuCategoryDTO) {
        Owner owner = userService.getCurrentOwner();
        MenuCategory menu = menuCategoryMapper.map(menuCategoryDTO);
        menuService.addMenuCategoryToMenu(owner, menu);
        return "redirect:/owner/category";
    }

    @PostMapping(value = DELETE_CATEGORY)
    public String deleteMenuCategory(@RequestParam Long menuCategoryId) {
        menuCategoryService.deleteMenuCategoryById(menuCategoryId);
        log.info("MenuCategory with ID: [%s] was delete".formatted(menuCategoryId));
        return "redirect:/owner/category";
    }
}
