package pl.foodflow.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.foodflow.infrastructure.database.entity.RestaurantCategoryEntity;

@Repository
public interface RestaurantCategoryJpaRepository extends JpaRepository<RestaurantCategoryEntity, Long> {
}
