package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.OwnerDAO;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.Restaurant;

import java.util.Objects;

@Service
@AllArgsConstructor
public class OwnerService {

    private final RestaurantService restaurantService;
    private final OwnerDAO ownerDAO;

    @Transactional
    public void createRestaurant(Restaurant restaurant) {
        if (Objects.nonNull(restaurant.getRestaurantId())) {
            throw new IllegalArgumentException("New restaurant should not have a restaurant ID.");
        }
        restaurantService.saveRestaurant(restaurant);
    }

    @Transactional
    public void saveOwner(Owner owner) {
        ownerDAO.saveOwner(owner);
    }
}
