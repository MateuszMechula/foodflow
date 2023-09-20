package pl.foodflow.api.controller.customer;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.foodflow.api.dto.SearchAddressDTO;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.business.SearchRestaurantService;
import pl.foodflow.domain.Restaurant;

import java.util.List;

import static pl.foodflow.api.controller.customer.CustomerSearchRestaurantController.CUSTOMER;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping(value = CUSTOMER)
public class CustomerSearchRestaurantController {

    public static final String CUSTOMER = "/customer";
    public static final String SEARCH_RESTAURANTS = "/search-restaurants";

    private final RestaurantService restaurantService;
    private final SearchRestaurantService searchRestaurantService;

    @GetMapping(value = SEARCH_RESTAURANTS)
    public String showSearchForm(Model model) {
        log.info("Received request to show restaurant search form.");
        model.addAttribute("searchAddressDTO", new SearchAddressDTO());
        return "customer_search_restaurant";
    }

    @PostMapping(value = SEARCH_RESTAURANTS)
    public String searchRestaurants(
            @Valid @ModelAttribute SearchAddressDTO searchAddressDTO,
            HttpSession session,
            Model model) {
        log.info("Received request to search for restaurants based on address");

        List<Restaurant> allRestaurants = restaurantService.findAll();
        List<Restaurant> matchingRestaurants = searchRestaurantService.filterMatchingRestaurants
                (searchAddressDTO, allRestaurants);

        model.addAttribute("matchingRestaurants", matchingRestaurants);
        session.setAttribute("searchAddressDTO", searchAddressDTO);

        return "customer_search_restaurant";
    }
}
