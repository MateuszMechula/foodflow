package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.OwnerDAO;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.Restaurant;

import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerDAO ownerDAO;

    public List<Owner> findAll() {
        List<Owner> allOwners = ownerDAO.findAll();
        log.info("Owners : [{}]", allOwners.size());
        return allOwners;
    }

//    @Transactional
//    public void createRestaurant(Restaurant restaurant) {
//        if (Objects.nonNull(restaurant.getRestaurantId())) {
//            throw new IllegalArgumentException("New restaurant should not have a restaurant ID.");
//        }
//        restaurantService.addRestaurant(restaurant);
//    }

    @Transactional
    public void saveOwner(Owner owner) {
        ownerDAO.saveOwner(owner);
    }

    @Transactional
    public Owner findByEmail(String email) {
        return ownerDAO.findByEmail(email);
    }

    @Transactional
    public void update(Owner owner) {
        Owner existingOwner = ownerDAO.findById(owner.getOwnerId())
                .orElseThrow();

        Owner updatedOwner = existingOwner
                .withName(owner.getName())
                .withSurname(owner.getSurname())
                .withEmail(owner.getEmail())
                .withPhone(owner.getPhone())
                .withAddress(owner.getAddress())
                .withRestaurant(owner.getRestaurant());

        ownerDAO.saveOwner(updatedOwner);
    }
}
