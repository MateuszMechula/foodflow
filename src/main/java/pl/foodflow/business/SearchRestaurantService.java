package pl.foodflow.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.foodflow.api.dto.SearchAddressDTO;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.domain.RestaurantAddress;

import java.util.List;

@Controller
@AllArgsConstructor
public class SearchRestaurantService {

    public List<Restaurant> filterMatchingRestaurants(SearchAddressDTO searchAddressDTO, List<Restaurant> allRestaurants) {
        return allRestaurants.stream()
                .filter(restaurant ->
                        restaurant.getRestaurantAddresses().stream()
                                .map(RestaurantAddress::getAddress)
                                .anyMatch(address ->
                                        extractStreetName(searchAddressDTO.getStreet()).equalsIgnoreCase(address.getStreet()) &&
                                                searchAddressDTO.getPostalCode().contains(address.getPostalCode()) &&
                                                searchAddressDTO.getCity().equalsIgnoreCase(address.getCity())))
                .toList();
    }

    private String extractStreetName(String fullAddress) {
        String[] parts = fullAddress.split(" ");

        if (parts.length == 3) {
            return parts[0] + " " + parts[1];
        } else return parts[0];
    }
}
