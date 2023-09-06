package pl.foodflow.business.dao;

import pl.foodflow.domain.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuDAO {

    Optional<Menu> findById(Long menuId);

    void saveMenu(Menu menu);

    List<Menu> findAll();

    void deleteMenuById(Long menuId);
}
