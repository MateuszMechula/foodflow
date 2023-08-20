package pl.foodflow.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.foodflow.api.dto.OwnerDTO;
import pl.foodflow.api.dto.RestaurantDTO;
import pl.foodflow.api.dto.mapper.RestaurantMapper;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.business.dao.OwnerDAO;

@Controller
@AllArgsConstructor
public class RestaurantController {

    public static final String RESTAURANT = "/restaurant";
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;
    private final OwnerDAO ownerDAO;

    @GetMapping(value = RESTAURANT)
    public ModelAndView mechanicCheckPage(Model model) {
        var allOwners = ownerDAO.findAll();
        model.addAttribute("restaurantDTO", new RestaurantDTO());
        model.addAttribute("allOwners", allOwners);

        return new ModelAndView("owner_restaurant");
    }

    @PostMapping(value = RESTAURANT)
    public String addRestaurant(
            @ModelAttribute("restaurantDTO") RestaurantDTO restaurantDTO
    ) {
        ModelAndView modelAndView = new ModelAndView("owner_restaurant");
        modelAndView.addObject("restaurantDTO", restaurantDTO); // Dodaj nowy obiekt do modelu
        return "redirect:/owner_restaurant.html";
    }
}
