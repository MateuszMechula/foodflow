package pl.foodflow.business;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.MenuDAO;
import pl.foodflow.business.exceptions.ThatRestaurantHasAMenu;
import pl.foodflow.domain.Menu;
import pl.foodflow.domain.Restaurant;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuDAO menuDAO;
    private final RestaurantService restaurantService;

    public List<Menu> findAll() {
        return menuDAO.findAll();
    }

    public Menu findMenuById(Long menuId) {
        return menuDAO.findById(menuId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Menu with id: [%s] not found".formatted(menuId)
                ));
    }

    @Transactional
    public void addMenuToRestaurant(String nip, Menu menu) {
        Restaurant existingRestaurant = restaurantService.findRestaurantByNip(nip);
        if (Objects.nonNull(existingRestaurant.getMenu())) {
            throw new ThatRestaurantHasAMenu(
                    "Restaurant with nip [%s] has a menu. You can't create more than one"
                            .formatted(existingRestaurant.getNip()));
        }
        Menu menuToAdd = buildMenu(menu, existingRestaurant);

        menuDAO.saveMenu(menuToAdd);
    }

    private Menu buildMenu(Menu menu, Restaurant restaurant) {
        return Menu.builder()
                .name(menu.getName())
                .description(menu.getDescription())
                .restaurant(restaurant)
                .build();
    }
}
