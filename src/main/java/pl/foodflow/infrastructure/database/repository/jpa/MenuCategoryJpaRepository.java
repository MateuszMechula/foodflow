package pl.foodflow.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.foodflow.infrastructure.database.entity.MenuCategoryEntity;

import java.util.List;

@Repository
public interface MenuCategoryJpaRepository extends JpaRepository<MenuCategoryEntity, Long> {
    List<MenuCategoryEntity> findAllByMenuMenuId(Long menuId);
}
