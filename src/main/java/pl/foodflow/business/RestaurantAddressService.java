package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.RestaurantAddressDAO;
import pl.foodflow.domain.RestaurantAddress;

@Service
@AllArgsConstructor
public class RestaurantAddressService {
    private final RestaurantAddressDAO restaurantAddressDAO;

    public RestaurantAddress findById(Long addressId) {
        return restaurantAddressDAO.findById(addressId).orElseThrow();
    }

    @Transactional
    public void deleteRestaurantAddress(RestaurantAddress restaurantAddress) {
        restaurantAddressDAO.deleteRestaurantAddress(restaurantAddress);
    }

    public RestaurantAddress findByAddressId(Long addressId) {
        return restaurantAddressDAO.findByAddressId(addressId).orElseThrow();
    }
}
