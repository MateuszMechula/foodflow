package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.AddressDAO;
import pl.foodflow.business.dao.RestaurantDAO;
import pl.foodflow.business.exceptions.RestaurantNotFound;
import pl.foodflow.business.exceptions.ThatRestaurantHasAMenu;
import pl.foodflow.domain.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;
    private final RestaurantAddressService restaurantAddressService;
    private final AddressDAO addressDAO;
    private final MenuService menuService;

    public Restaurant findById(Long restaurantId) {
        return restaurantDAO.findById(restaurantId).orElseThrow(() -> new RestaurantNotFound
                ("Restaurant with ID: [%s] not found".formatted(restaurantId)));
    }

    public List<Restaurant> findAll() {
        return restaurantDAO.findAll();
    }

    @Transactional
    public void createRestaurant(Restaurant restaurant) {
        restaurantDAO.saveRestaurant(restaurant);
        log.info("Restaurant added successfully.");
    }

    @Transactional
    public void addMenuToRestaurant(Owner owner, Menu menu) {
        if (Objects.isNull(owner.getRestaurant())) {
            throw new RestaurantNotFound("To add a menu you have to create restaurant first");
        }
        Restaurant restaurant = owner.getRestaurant();

        if (Objects.nonNull(restaurant.getMenu())) {
            throw new ThatRestaurantHasAMenu
                    ("Restaurant with nip [%s] has a menu. You can't create more than one"
                            .formatted(restaurant.getNip()));
        }
        menuService.saveMenu(menu.withRestaurant(restaurant));
    }


    @Transactional
    public void addDeliveryAddressToRestaurant(Address deliveryAddress, Owner owner) {

        Restaurant restaurant = owner.getRestaurant();
        Menu menu = owner.getRestaurant().getMenu();
        Address address = owner.getRestaurant().getAddress();

        RestaurantAddress restaurantAddress = buildRestaurantAddress(deliveryAddress, restaurant);

        Address savedAddress = addressDAO.saveAddress(deliveryAddress);
        RestaurantAddress savedRestaurantAddress = restaurantAddressService
                .saveRestaurantAddress(restaurantAddress.withAddress(savedAddress));

        Set<RestaurantAddress> restaurantAddresses = restaurant.getRestaurantAddresses();
        restaurantAddresses.add(savedRestaurantAddress);

        Restaurant updatedRestaurant = restaurant
                .withMenu(menu)
                .withAddress(address.withRestaurant(restaurant))
                .withRestaurantAddresses(restaurantAddresses)
                .withOwner(owner);

        restaurantDAO.saveRestaurant(updatedRestaurant);
    }

    private RestaurantAddress buildRestaurantAddress(Address address, Restaurant restaurant) {
        return RestaurantAddress.builder()
                .restaurant(restaurant)
                .address(address)
                .build();
    }
}
