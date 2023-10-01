package pl.foodflow.infrastructure.database.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.domain.OrderItem;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.infrastructure.database.entity.OrderItemEntity;
import pl.foodflow.infrastructure.database.entity.OrderRecordEntity;
import pl.foodflow.infrastructure.database.repository.jpa.OrderItemJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.OrderItemEntityMapper;
import pl.foodflow.infrastructure.database.repository.mapper.OrderRecordEntityMapper;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.foodflow.util.TestDataFactory.*;

@ExtendWith(MockitoExtension.class)
class OrderItemRepositoryTest {
    @InjectMocks
    private OrderItemRepository orderItemRepository;
    @Mock
    private OrderItemJpaRepository orderItemJpaRepository;
    @Mock
    private OrderItemEntityMapper orderItemEntityMapper;
    @Mock
    private OrderRecordEntityMapper orderRecordEntityMapper;

    @Test
    void shouldSaveOrderItem() {
        //given
        OrderItem orderItem = someOrderItem1();
        OrderItemEntity orderItemEntity = someOrderItemEntity1();

        when(orderItemEntityMapper.mapToEntity(orderItem)).thenReturn(orderItemEntity);
        when(orderItemJpaRepository.save(orderItemEntity)).thenReturn(orderItemEntity);
        when(orderItemEntityMapper.mapFromEntity(orderItemEntity)).thenReturn(orderItem);
        //when
        OrderItem foundOrderItem = orderItemRepository.saveOrderItem(orderItem);
        //then
        Assertions.assertThat(foundOrderItem)
                .isNotNull()
                .isEqualTo(orderItem);
        verify(orderItemEntityMapper).mapToEntity(orderItem);
        verify(orderItemJpaRepository).save(orderItemEntity);
        verify(orderItemEntityMapper).mapFromEntity(orderItemEntity);
    }

    @Test
    void shouldDeleteOrderItemByOrderRecord() {
        //given
        OrderRecord orderRecord = someOrderRecord1();
        OrderRecordEntity orderRecordEntity = someOrderRecordEntity1();

        when(orderRecordEntityMapper.mapToEntity(orderRecord)).thenReturn(orderRecordEntity);
        //when
        orderItemRepository.deleteOrderItemByOrderRecord(orderRecord);
        //then
        verify(orderItemJpaRepository).deleteByOrderRecord(orderRecordEntity);
    }
}