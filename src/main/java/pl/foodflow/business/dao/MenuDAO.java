package pl.foodflow.business.dao;

import pl.foodflow.domain.Menu;

import java.util.Optional;

public interface MenuDAO {
    Optional<Menu> findMenuById(Long menuId);

    void saveMenu(Menu menu);

    void deleteMenuById(Long menuId);
}
