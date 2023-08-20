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
import pl.foodflow.business.dao.OwnerDAO;
import pl.foodflow.domain.Restaurant;

import java.util.Map;

@Controller
@AllArgsConstructor
public class RestaurantController {

    public static final String RESTAURANT = "/restaurant";
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;
    private final OwnerDAO ownerDAO;

    @GetMapping(value = RESTAURANT)
    public ModelAndView mechanicCheckPage() {
        var allOwners = ownerDAO.findAll();
        Map<String, ?> model = Map.of(
                "restaurantDTO", RestaurantDTO.buildDefault(),
                "allOwners", allOwners
        );
        return new ModelAndView("owner_restaurant", model);
    }

    @PostMapping(value = RESTAURANT)
    public String addRestaurant(
            @ModelAttribute("restaurantDTO") RestaurantDTO restaurantDTO
    ) {

        Restaurant restaurant = restaurantMapper.map(restaurantDTO);
        restaurantService.createRestaurant(restaurant);

        ModelAndView modelAndView = new ModelAndView("owner_restaurant");
        modelAndView.addObject("restaurantDTO", restaurantDTO);
        return "redirect:/";
    }
}
