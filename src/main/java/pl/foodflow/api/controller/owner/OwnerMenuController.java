package pl.foodflow.api.controller.owner;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.foodflow.api.dto.MenuDTO;
import pl.foodflow.api.dto.mapper.MenuMapper;
import pl.foodflow.business.MenuService;
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.security.user.UserService;

import java.util.HashMap;
import java.util.Map;

import static pl.foodflow.api.controller.owner.OwnerMenuController.OWNER;

@Controller
@AllArgsConstructor
@RequestMapping(value = OWNER)
public class OwnerMenuController {

    public static final String OWNER = "/owner";
    public static final String MENU = "/menu";
    public static final String DELETE_MENU = "/delete-menu";

    private final MenuMapper menuMapper;
    private final MenuService menuService;
    private final UserService userService;

    @GetMapping(value = MENU)
    public ModelAndView menuSection() {
        Owner owner = userService.getCurrentOwner();
        Menu menu = (owner != null && owner.getRestaurant() != null) ? owner.getRestaurant().getMenu() : null;

        Map<String, Object> model = new HashMap<>();
        model.put("menuDTO", MenuDTO.buildDefault());
        if (menu != null) {
            model.put("menu", menu);
        }
        return new ModelAndView("owner_menu", model);
    }

    @PostMapping(value = MENU)
    public String addMenu(@Valid @ModelAttribute("menuDTO") MenuDTO menuDTO) {
        Owner owner = userService.getCurrentOwner();
        Menu menu = menuMapper.map(menuDTO);
        menuService.createMenuForRestaurant(owner, menu);
        return "redirect:/owner/menu";
    }

    @PostMapping(value = DELETE_MENU)
    public String deleteMenu(@RequestParam Long menuId) {
        menuService.deleteMenu(menuId);
        return "redirect:/owner/menu";
    }
}
