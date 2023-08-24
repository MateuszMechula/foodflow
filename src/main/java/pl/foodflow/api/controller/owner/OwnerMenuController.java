package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.foodflow.api.dto.MenuDTO;
import pl.foodflow.api.dto.mapper.MenuMapper;
import pl.foodflow.business.MenuService;
import pl.foodflow.business.OwnerService;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.security.UserService;

import java.util.Map;

import static pl.foodflow.api.controller.owner.OwnerMenuController.OWNER;

@Controller
@AllArgsConstructor
@RequestMapping(value = OWNER)
public class OwnerMenuController {

    public static final String OWNER = "/owner";
    public static final String MENU = "/menu";
    private final MenuMapper menuMapper;
    private final MenuService menuService;
    private final RestaurantService restaurantService;
    private final UserService userService;
    private final OwnerService ownerService;

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
            Authentication authentication
    ) {
        String username = authentication.getName();
        int userId = userService.findByUserName(username).getUserId();
        Owner owner = ownerService.findByUserIdWithMenuAndCategoryAndItems(userId);

        Menu menu = menuMapper.map(menuDTO);
        menuService.addMenuToRestaurant(owner, menu);

        return "redirect:/owner";
    }
}
