package pl.foodflow.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.foodflow.business.dao.MenuDAO;
import pl.foodflow.domain.Menu;
import pl.foodflow.infrastructure.database.entity.MenuEntity;
import pl.foodflow.infrastructure.database.repository.jpa.MenuJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.MenuEntityMapper;

import java.util.List;
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
    public List<Menu> findAll() {
        return menuJpaRepository.findAll().stream()
                .map(menuEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public void deleteMenu(Long menuId) {
        menuJpaRepository.deleteById(menuId);
    }

    @Override
    public void saveMenu(Menu menu) {
        MenuEntity toSave = menuEntityMapper.mapToEntity(menu);
        MenuEntity saved = menuJpaRepository.saveAndFlush(toSave);
        menuEntityMapper.mapFromEntity(saved);
    }
}
