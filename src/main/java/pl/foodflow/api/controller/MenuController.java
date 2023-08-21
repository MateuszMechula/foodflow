package pl.foodflow.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.foodflow.api.dto.MenuDTO;
import pl.foodflow.api.dto.mapper.MenuMapper;
import pl.foodflow.business.MenuService;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.domain.Menu;

import java.util.Map;

@Controller
@AllArgsConstructor
public class MenuController {

    public static final String MENU = "/menu";
    private final MenuMapper menuMapper;
    private final MenuService menuService;
    private final RestaurantService restaurantService;

    @GetMapping(value = MENU)
    public ModelAndView menuSection() {
        var allRestaurants = restaurantService.findAll();
        Map<String, ?> model = Map.of(
                "menuDTO", MenuDTO.buildDefault(),
                "allRestaurants", allRestaurants
        );

        return new ModelAndView("owner_menu", model);
    }

    @PostMapping(value = MENU)
    public String addMenu(
            @ModelAttribute("menuDTO") MenuDTO menuDTO,
            @RequestParam("restaurantNIP") String restaurantNIP
    ) {
        Menu menu = menuMapper.map(menuDTO);
        menuService.addMenuToRestaurant(restaurantNIP, menu);

        ModelAndView modelAndView = new ModelAndView("owner_menu");
        modelAndView.addObject("menuDTO", menuDTO);
        return "redirect:/";
    }
}
