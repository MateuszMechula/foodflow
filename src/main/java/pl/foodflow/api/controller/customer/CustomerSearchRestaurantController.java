package pl.foodflow.api.controller.customer;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public static final String PAGE = "/page";

    private final RestaurantService restaurantService;
    private final SearchRestaurantService searchRestaurantService;


    @GetMapping(value = SEARCH_RESTAURANTS)
    public String searchRestaurantsForm(Model model) {
        log.info("Received request to show restaurant search form.");
        model.addAttribute("searchAddressDTO", new SearchAddressDTO());
        return "customer_search_restaurant_form";
    }

    @GetMapping(value = SEARCH_RESTAURANTS + PAGE)
    public String findPaginated(@Valid @ModelAttribute SearchAddressDTO searchAddressDTO,
                                @RequestParam(value = "pageNo", required = false, defaultValue = "1") int pageNo,
                                @RequestParam(name = "sort", required = false) String sortColumn,
                                Model model,
                                HttpSession session) {
        session.setAttribute("searchAddressDTO", searchAddressDTO);
        int pageSize = 5;

        if (sortColumn == null || sortColumn.isEmpty()) {
            sortColumn = "defaultSortColumn";
        }

        Page<Restaurant> page = restaurantService.findPaginated(pageNo, pageSize, sortColumn);
        List<Restaurant> matchingRestaurants = searchRestaurantService
                .filterMatchingRestaurants(searchAddressDTO, page.getContent());

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listRestaurants", matchingRestaurants);
        model.addAttribute("searchAddressDTO", searchAddressDTO);
        return "customer_search_restaurant_table";
    }
}
