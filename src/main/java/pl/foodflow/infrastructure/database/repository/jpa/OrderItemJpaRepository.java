package pl.foodflow.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.foodflow.infrastructure.database.entity.OrderItemEntity;

import java.util.List;

@Repository
public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, Long> {

    @Modifying
    @Query("DELETE FROM OrderItemEntity oi WHERE oi.orderRecord.orderRecordId = :orderRecordId")
    void deleteByOrderRecordId(@Param("orderRecordId") Long orderRecordId);

    List<OrderItemEntity> findOrderItemEntitiesByCategoryItemCategoryItemId(Long categoryItemId);
}
