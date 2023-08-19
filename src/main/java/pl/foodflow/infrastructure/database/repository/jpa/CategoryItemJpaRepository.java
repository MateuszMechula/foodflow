package pl.foodflow.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.foodflow.infrastructure.database.entity.CategoryItemEntity;

@Repository
public interface CategoryItemJpaRepository extends JpaRepository<CategoryItemEntity, Long> {
}
