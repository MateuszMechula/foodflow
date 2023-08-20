package pl.foodflow.business.dao;

import pl.foodflow.domain.Menu;

import java.util.Optional;

public interface MenuDAO {

    Optional<Menu> findById(Long menuId);

    Menu saveMenu(Menu menu);
}