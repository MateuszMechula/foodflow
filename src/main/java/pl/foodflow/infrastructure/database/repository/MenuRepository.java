package pl.foodflow.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.foodflow.business.dao.MenuDAO;
import pl.foodflow.domain.Menu;
import pl.foodflow.infrastructure.database.entity.MenuEntity;
import pl.foodflow.infrastructure.database.repository.jpa.MenuJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.MenuEntityMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class MenuRepository implements MenuDAO {

    private final MenuJpaRepository menuJpaRepository;
    private final MenuEntityMapper menuEntityMapper;


    @Override
    public Optional<Menu> findMenuById(Long menuId) {
        return menuJpaRepository.findById(menuId)
                .map(menuEntityMapper::mapFromEntity);
    }

    @Override
    public void saveMenu(Menu menu) {
        MenuEntity toSave = menuEntityMapper.mapToEntity(menu);
        MenuEntity saved = menuJpaRepository.saveAndFlush(toSave);
        menuEntityMapper.mapFromEntity(saved);
    }

    @Override
    public void deleteMenuById(Long menuId) {
        menuJpaRepository.deleteById(menuId);
    }
}
