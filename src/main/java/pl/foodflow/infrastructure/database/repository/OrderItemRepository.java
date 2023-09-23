package pl.foodflow.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.foodflow.business.dao.OrderItemDAO;
import pl.foodflow.domain.OrderItem;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.infrastructure.database.entity.OrderItemEntity;
import pl.foodflow.infrastructure.database.entity.OrderRecordEntity;
import pl.foodflow.infrastructure.database.repository.jpa.OrderItemJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.OrderItemEntityMapper;
import pl.foodflow.infrastructure.database.repository.mapper.OrderRecordEntityMapper;

@Repository
@AllArgsConstructor
public class OrderItemRepository implements OrderItemDAO {

    private final OrderItemJpaRepository orderItemJpaRepository;
    private final OrderItemEntityMapper orderItemEntityMapper;
    private final OrderRecordEntityMapper orderRecordEntityMapper;

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        OrderItemEntity toSave = orderItemEntityMapper.mapToEntity(orderItem);
        OrderItemEntity saved = orderItemJpaRepository.save(toSave);
        return orderItemEntityMapper.mapFromEntity(saved);
    }

    @Override
    public void deleteOrderItemByOrderRecord(OrderRecord orderRecord) {
        OrderRecordEntity orderItemByOrderRecord = orderRecordEntityMapper.mapToEntity(orderRecord);
        orderItemJpaRepository.deleteByOrderRecord(orderItemByOrderRecord);
    }
}
