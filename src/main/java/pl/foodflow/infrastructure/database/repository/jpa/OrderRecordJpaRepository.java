package pl.foodflow.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.foodflow.infrastructure.database.entity.OrderRecordEntity;

import java.util.Optional;

@Repository
public interface OrderRecordJpaRepository extends JpaRepository<OrderRecordEntity, Long> {

    @Query("SELECT o FROM OrderRecordEntity o LEFT JOIN FETCH o.orderItems oi LEFT JOIN FETCH oi.categoryItem WHERE o.orderRecordId = :id")
    Optional<OrderRecordEntity> findOrderRecordById(Long id);
}
