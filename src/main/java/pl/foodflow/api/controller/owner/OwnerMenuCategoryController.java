package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.foodflow.api.dto.MenuCategoryDTO;
import pl.foodflow.api.dto.mapper.MenuCategoryMapper;
import pl.foodflow.business.MenuCategoryService;
import pl.foodflow.business.MenuService;
import pl.foodflow.business.OwnerService;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.security.UserService;

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

    private final MenuCategoryService menuCategoryService;
    private final MenuService menuService;
    private final MenuCategoryMapper menuCategoryMapper;
    private final UserService userService;
    private final OwnerService ownerService;

    @GetMapping(value = CATEGORY)
    public ModelAndView menuCategorySection(Authentication authentication) {
        String username = authentication.getName();
        int userId = userService.findByUserName(username).getUserId();
        Owner owner = ownerService.findByUserIdWithMenuAndCategoryAndItems(userId);

        if (owner != null && owner.getRestaurant() != null && owner.getRestaurant().getMenu() != null) {
            var allMenuCategories = menuCategoryService.findAllByMenuId(owner.getRestaurant().getMenu().getMenuId());

            if (allMenuCategories != null) {
                Map<String, ?> model = Map.of(
                        "categoryDTO", MenuCategoryDTO.buildDefault(),
                        "allMenuCategories", allMenuCategories
                );
                return new ModelAndView("owner_menu_category", model);
            }
        }
        Map<String, ?> model = Map.of(
                "categoryDTO", MenuCategoryDTO.buildDefault()
        );
        return new ModelAndView("owner_menu_category", model);
    }

    @PostMapping(value = CATEGORY)
    public String addMenuCategory(
            @ModelAttribute("categoryDTO") MenuCategoryDTO menuCategoryDTO,
            Authentication authentication
    ) {
        String username = authentication.getName();
        int userId = userService.findByUserName(username).getUserId();
        Owner owner = ownerService.findByUserIdWithMenuAndCategoryAndItems(userId);


        MenuCategory menu = menuCategoryMapper.map(menuCategoryDTO);
        menuService.addCategoryToMenu(owner, menu);

        return "redirect:/owner/category";
    }

    @PostMapping(value = DELETE_CATEGORY)
    public String deleteMenuCategory(@RequestParam Long menuCategoryId) {

        menuCategoryService.deleteMenuCategoryById(menuCategoryId);
        log.info("MenuCategory with ID: [%s] was delete".formatted(menuCategoryId));
        return "redirect:/owner/category";
    }
}
