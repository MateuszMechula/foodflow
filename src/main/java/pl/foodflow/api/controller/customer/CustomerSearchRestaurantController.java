package pl.foodflow.api.controller.customer;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.foodflow.api.dto.SearchAddressDTO;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.domain.RestaurantAddress;

import java.util.List;

import static pl.foodflow.api.controller.customer.CustomerSearchRestaurantController.CUSTOMER;


@Controller
@AllArgsConstructor
@RequestMapping(value = CUSTOMER)
public class CustomerSearchRestaurantController {

    public static final String CUSTOMER = "/customer";
    public static final String SEARCH_RESTAURANTS = "/search-restaurants";

    private final RestaurantService restaurantService;

    @GetMapping(value = SEARCH_RESTAURANTS)
    public String showSearchForm(Model model) {

        model.addAttribute("searchAddressDTO", new SearchAddressDTO());

        return "customer_search_restaurant";
    }

    @PostMapping(value = SEARCH_RESTAURANTS)
    public String searchRestaurants(
            @ModelAttribute SearchAddressDTO searchAddressDTO,
            HttpSession session,
            Model model) {
        List<Restaurant> allRestaurants = restaurantService.findAll();

        List<Restaurant> matchingRestaurants = allRestaurants.stream()
                .filter(restaurant ->
                        restaurant.getRestaurantAddresses().stream()
                                .map(RestaurantAddress::getAddress)
                                .anyMatch(address ->
                                        extractStreetName(searchAddressDTO.getStreet()).equalsIgnoreCase(address.getStreet()) &&
                                                searchAddressDTO.getPostalCode().contains(address.getPostalCode()) &&
                                                searchAddressDTO.getCity().equalsIgnoreCase(address.getCity())))
                .toList();
        model.addAttribute("matchingRestaurants", matchingRestaurants);
        session.setAttribute("searchAddressDTO", searchAddressDTO);
        return "customer_search_restaurant";
    }

    private String extractStreetName(String fullAddress) {
        String[] parts = fullAddress.split(" ");
        if (parts.length > 0) {
            return parts[0];
        }
        return "";
    }
}
