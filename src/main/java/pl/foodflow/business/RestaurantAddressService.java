package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.RestaurantAddressDAO;
import pl.foodflow.business.exceptions.RestaurantAddressNotFoundException;
import pl.foodflow.domain.RestaurantAddress;

@Service
@AllArgsConstructor
public class RestaurantAddressService {
    private final RestaurantAddressDAO restaurantAddressDAO;

    public RestaurantAddress findById(Long restaurantAddressId) {
        return restaurantAddressDAO.findById(restaurantAddressId)
                .orElseThrow(() -> new RestaurantAddressNotFoundException
                        ("RestaurantAddress with ID: [%s] not found".formatted(restaurantAddressId)));
    }

    @Transactional
    public RestaurantAddress findRestaurantAddressByAddressId(Long addressId) {
        return restaurantAddressDAO.findRestaurantAddressByAddressId(addressId).orElseThrow();
    }
    @Transactional
    public void deleteRestaurantAddress(RestaurantAddress restaurantAddress) {
        restaurantAddressDAO.deleteRestaurantAddress(restaurantAddress);
    }

    @Transactional
    public RestaurantAddress saveRestaurantAddress(RestaurantAddress restaurantAddress) {
       return restaurantAddressDAO.saveRestaurantAddress(restaurantAddress);
    }
}
