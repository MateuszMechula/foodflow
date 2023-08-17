package pl.foodflow.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.foodflow.infrastructure.database.entity.OrderRecordEntity;

@Repository
public interface OrderRecordJpaRepository extends JpaRepository<OrderRecordEntity, Long> {
}
