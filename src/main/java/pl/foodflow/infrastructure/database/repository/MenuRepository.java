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
    public Optional<Menu> findById(Long menuId) {
        return menuJpaRepository.findById(menuId)
                .map(menuEntityMapper::mapFromEntity);
    }

    @Override
    public Menu saveMenu(Menu menu) {
        MenuEntity toSave = menuEntityMapper.mapToEntity(menu);
        MenuEntity saved = menuJpaRepository.save(toSave);
        return menuEntityMapper.mapFromEntity(saved);
    }
}
