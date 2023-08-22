package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
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

@Controller
@AllArgsConstructor
@RequestMapping(value = OWNER)
public class OwnerMenuCategoryController {
    public static final String OWNER = "/owner";
    public static final String CATEGORY = "/category";

    private final MenuService menuService;
    private final MenuCategoryService menuCategoryService;
    private final MenuCategoryMapper menuCategoryMapper;
    private final UserService userService;
    private final OwnerService ownerService;

    @GetMapping(value = CATEGORY)
    public ModelAndView menuCategorySection() {
        var allMenus = menuService.findAll();
        Map<String, ?> model = Map.of(
                "categoryDTO", MenuCategoryDTO.buildDefault(),
                "allMenus", allMenus
        );

        return new ModelAndView("owner_menu_category", model);
    }

    @PostMapping(value = CATEGORY)
    public String addMenu(
            @ModelAttribute("categoryDTO") MenuCategoryDTO menuCategoryDTO,
            @RequestParam("menuId") String menuId,
            Authentication authentication
    ) {
        String username = authentication.getName();
        int userId = userService.findByUserName(username).getUser_id();
        Owner owner = ownerService.findByUserIdWithMenuAndCategoryAndItems(userId);


        MenuCategory menu = menuCategoryMapper.map(menuCategoryDTO);
        menuCategoryService.addCategoryToMenu(menuId, menu);

        ModelAndView modelAndView = new ModelAndView("owner_menu_category");
        modelAndView.addObject("categoryDTO", menuCategoryDTO);
        return "redirect:/owner";
    }
}
