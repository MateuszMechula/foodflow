package pl.foodflow.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.foodflow.api.dto.RestaurantDTO;
import pl.foodflow.api.dto.mapper.RestaurantMapper;
import pl.foodflow.business.RestaurantService;

@Controller
@AllArgsConstructor
public class RestaurantController {

    public static final String RESTAURANT = "/restaurant";
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;

    @GetMapping(value = RESTAURANT)
    public ModelAndView mechanicCheckPage() {
        return new ModelAndView("add_restaurant");
    }

    @PostMapping(RESTAURANT)
    public ModelAndView addRestaurant(
            @ModelAttribute("restaurantDTO") RestaurantDTO restaurantDTO
    ) {
        ModelAndView modelAndView = new ModelAndView("add_restaurant");
        modelAndView.addObject("restaurantDTO", new RestaurantDTO()); // Dodaj nowy obiekt do modelu
        return modelAndView;
    }
}
