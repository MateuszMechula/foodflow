package pl.foodflow.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.foodflow.business.dao.MenuCategoryDAO;
import pl.foodflow.domain.MenuCategory;
import pl.foodflow.infrastructure.database.entity.MenuCategoryEntity;
import pl.foodflow.infrastructure.database.repository.jpa.MenuCategoryJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.MenuCategoryEntityMapper;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class MenuCategoryRepository implements MenuCategoryDAO {

    private final MenuCategoryJpaRepository menuCategoryJpaRepository;
    private final MenuCategoryEntityMapper menuCategoryEntityMapper;

    @Override
    public Optional<MenuCategory> findMenuCategoryById(Long menuCategoryId) {
        return menuCategoryJpaRepository.findById(menuCategoryId)
                .map(menuCategoryEntityMapper::mapFromEntity);
    }

    @Override
    public List<MenuCategory> findAllCategoriesByMenuId(Long menuId) {
        return menuCategoryJpaRepository.findAllByMenuMenuId(menuId).stream()
                .map(menuCategoryEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public void saveMenuCategory(MenuCategory menuCategory) {
        MenuCategoryEntity toSave = menuCategoryEntityMapper.mapToEntity(menuCategory);
        MenuCategoryEntity saved = menuCategoryJpaRepository.save(toSave);
        menuCategoryEntityMapper.mapFromEntity(saved);
    }

    @Override
    public void deleteMenuCategoryById(Long menuCategoryId) {
        menuCategoryJpaRepository.deleteById(menuCategoryId);
    }
}
