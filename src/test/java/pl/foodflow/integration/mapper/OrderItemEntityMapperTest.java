package pl.foodflow.integration.mapper;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.foodflow.domain.OrderItem;
import pl.foodflow.infrastructure.database.entity.OrderItemEntity;
import pl.foodflow.infrastructure.database.repository.mapper.OrderItemEntityMapper;
import pl.foodflow.util.TestDataForMappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OrderItemEntityMapperTest {
    private OrderItemEntityMapper orderItemEntityMapper;

    @Test
    void shouldMapOrderItemToOrderItemEntity() {
        //given
        OrderItem orderItem = TestDataForMappers.someOrderItem();
        //when
        OrderItemEntity orderItemEntity = orderItemEntityMapper.mapToEntity(orderItem);
        //then
        assertEquals(orderItemEntity.getOrderItemId(), orderItem.getOrderItemId());
        assertEquals(orderItemEntity.getUnitPrice(), orderItem.getUnitPrice());
        assertEquals(orderItemEntity.getQuantity(), orderItem.getQuantity());
    }

    @Test
    void shouldMapOrderItemEntityToOrderItem() {
        //given
        OrderItemEntity orderItemEntity = TestDataForMappers.someOrderItemEntity();
        //when
        OrderItem orderItem = orderItemEntityMapper.mapFromEntity(orderItemEntity);
        //then
        assertEquals(orderItem.getOrderItemId(), orderItemEntity.getOrderItemId());
        assertEquals(orderItem.getUnitPrice(), orderItemEntity.getUnitPrice());
        assertEquals(orderItem.getQuantity(), orderItemEntity.getQuantity());
    }
}