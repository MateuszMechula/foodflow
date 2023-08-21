package pl.foodflow.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.foodflow.api.dto.MenuCategoryDTO;
import pl.foodflow.api.dto.mapper.MenuCategoryMapper;
import pl.foodflow.business.MenuCategoryService;
import pl.foodflow.business.MenuService;
import pl.foodflow.domain.MenuCategory;

import java.util.Map;

@Controller
@AllArgsConstructor
public class MenuCategoryController {

    public static final String CATEGORY = "/category";
    private final MenuService menuService;
    private final MenuCategoryService menuCategoryService;
    private final MenuCategoryMapper menuCategoryMapper;

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
            @RequestParam("menuId") String menuId
    ) {
        MenuCategory menu = menuCategoryMapper.map(menuCategoryDTO);
        menuCategoryService.addCategoryToMenu(menuId, menu);

        ModelAndView modelAndView = new ModelAndView("owner_menu_category");
        modelAndView.addObject("categoryDTO", menuCategoryDTO);
        return "redirect:/";
    }
}
