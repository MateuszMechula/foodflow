package pl.foodflow.business;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.api.dto.SearchAddressDTO;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.domain.RestaurantAddress;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchRestaurantService {


    public List<Restaurant> filterMatchingRestaurants(
            SearchAddressDTO searchAddressDTO,
            List<Restaurant> allRestaurants) {

        List<Restaurant> matchingRestaurants = allRestaurants.stream()
                .filter(restaurant -> hasMatchingAddress(searchAddressDTO, restaurant))
                .collect(Collectors.toList());

        log.info("Found {} matching restaurants.", matchingRestaurants.size());
        return matchingRestaurants;
    }

    private boolean hasMatchingAddress(SearchAddressDTO searchAddressDTO, Restaurant restaurant) {
        return restaurant.getRestaurantAddresses().stream()
                .map(RestaurantAddress::getAddress)
                .anyMatch(address ->
                        matchesStreetName(searchAddressDTO.getStreet(), address.getStreet())
                                && searchAddressDTO.getPostalCode().contains(address.getPostalCode())
                                && searchAddressDTO.getCity().equalsIgnoreCase(address.getCity())
                );
    }

    private boolean matchesStreetName(String searchStreet, String restaurantStreet) {
        String searchStreetName = extractStreetName(searchStreet);
        String restaurantStreetName = extractStreetName(restaurantStreet);

        return searchStreetName.equalsIgnoreCase(restaurantStreetName);
    }

    private String extractStreetName(String fullAddress) {
        String[] parts = fullAddress.split(" ");

        if (parts.length == 3) {
            return parts[0] + " " + parts[1];
        } else return parts[0];
    }
}
