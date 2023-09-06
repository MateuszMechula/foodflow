package pl.foodflow.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.AddressDAO;
import pl.foodflow.domain.Address;

import java.util.Set;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressDAO addressDAO;

    public Set<Address> findAddressByRestaurantId(Long restaurantId) {
        return addressDAO.findAddressByRestaurantId(restaurantId);
    }
}
