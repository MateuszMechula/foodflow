package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.RestaurantAddressDAO;
import pl.foodflow.business.exceptions.RestaurantAddressNotFoundException;
import pl.foodflow.domain.RestaurantAddress;
import pl.foodflow.utils.ErrorMessages;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantAddressService {

    private final RestaurantAddressDAO restaurantAddressDAO;

    public RestaurantAddress findRestaurantAddressByAddressId(Long addressId) {
        log.info("Fetching RestaurantAddress with address ID: {}", addressId);
        return restaurantAddressDAO.findRestaurantAddressByAddressId(addressId)
                .orElseThrow(() -> new RestaurantAddressNotFoundException(
                        ErrorMessages.RESTAURANT_ADDRESS_WITH_ADDRESS_ID_NOT_FOUND.formatted(addressId)));
    }

    @Transactional
    public void deleteRestaurantAddress(RestaurantAddress restaurantAddress) {
        log.info("Deleting RestaurantAddress");
        restaurantAddressDAO.deleteRestaurantAddress(restaurantAddress);
        log.info("RestaurantAddress removed successfully");
    }

    @Transactional
    public RestaurantAddress saveRestaurantAddress(RestaurantAddress restaurantAddress) {
        log.info("Saving RestaurantAddress");
        return restaurantAddressDAO.saveRestaurantAddress(restaurantAddress);
    }
}
