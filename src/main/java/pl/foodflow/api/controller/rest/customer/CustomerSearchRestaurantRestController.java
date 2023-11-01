package pl.foodflow.api.controller.rest.customer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.foodflow.api.dto.SearchAddressDTO;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.business.SearchRestaurantService;
import pl.foodflow.domain.Restaurant;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = CustomerSearchRestaurantRestController.SEARCH_RESTAURANTS)
public class CustomerSearchRestaurantRestController {

    public static final String SEARCH_RESTAURANTS = "/api/v1/customer/search-restaurants";
    private final RestaurantService restaurantService;
    private final SearchRestaurantService searchRestaurantService;

    @GetMapping
    public ResponseEntity<List<Restaurant>> searchRestaurants(
            @RequestParam("street") String street,
            @RequestParam("postalCode") String postalCode,
            @RequestParam("city") String city) {

        SearchAddressDTO searchAddressDTO = SearchAddressDTO.builder()
                .street(street)
                .postalCode(postalCode)
                .city(city)
                .build();

//        List<Restaurant> allRestaurants = restaurantService.findAll();
//        List<Restaurant> matchingRestaurants = searchRestaurantService.filterMatchingRestaurants
//                (searchAddressDTO, allRestaurants);

        return (ResponseEntity<List<Restaurant>>) ResponseEntity.status(HttpStatus.OK);
    }
}
