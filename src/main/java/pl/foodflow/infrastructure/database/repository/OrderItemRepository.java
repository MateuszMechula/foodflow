package pl.foodflow.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.foodflow.business.dao.OrderItemDAO;
import pl.foodflow.domain.OrderItem;
import pl.foodflow.infrastructure.database.entity.OrderItemEntity;
import pl.foodflow.infrastructure.database.repository.jpa.OrderItemJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.OrderItemEntityMapper;

@Repository
@AllArgsConstructor
public class OrderItemRepository implements OrderItemDAO {

    private final OrderItemJpaRepository orderItemJpaRepository;
    private final OrderItemEntityMapper orderItemEntityMapper;

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        OrderItemEntity toSave = orderItemEntityMapper.mapToEntity(orderItem);
        OrderItemEntity saved = orderItemJpaRepository.save(toSave);
        return orderItemEntityMapper.mapFromEntity(saved);
    }

    @Override
    public void deleteOrderItemByOrderRecordId(Long orderRecordId) {
        orderItemJpaRepository.deleteByOrderRecordId(orderRecordId);
    }
}
