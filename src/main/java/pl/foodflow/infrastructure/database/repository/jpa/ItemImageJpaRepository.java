package pl.foodflow.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.foodflow.infrastructure.database.entity.ItemImageEntity;

@Repository
public interface ItemImageJpaRepository extends JpaRepository<ItemImageEntity, Long> {
}
