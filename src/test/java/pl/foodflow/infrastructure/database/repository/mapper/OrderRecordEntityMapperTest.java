package pl.foodflow.infrastructure.database.repository.mapper;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.infrastructure.database.entity.OrderRecordEntity;
import pl.foodflow.util.TestDataForMappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OrderRecordEntityMapperTest {
    private OrderRecordEntityMapper orderRecordEntityMapper;

    @Test
    void shouldMapOrderRecordToOrderRecordEntity() {
        //given
        OrderRecord orderRecord = TestDataForMappers.someOrderRecord();
        //when
        OrderRecordEntity orderRecordEntity = orderRecordEntityMapper.mapToEntity(orderRecord);
        //then
        assertEquals(orderRecordEntity.getOrderRecordId(), orderRecord.getOrderRecordId());
        assertEquals(orderRecordEntity.getOrderNumber(), orderRecord.getOrderNumber());
        assertEquals(orderRecordEntity.getOrderDateTime(), orderRecord.getOrderDateTime());
        assertEquals(orderRecordEntity.getOrderStatus(), orderRecord.getOrderStatus());
        assertEquals(orderRecordEntity.getOrderStatus(), orderRecord.getOrderStatus());
        assertEquals(orderRecordEntity.getTotalAmount(), orderRecord.getTotalAmount());
        assertEquals(orderRecordEntity.getContactPhone(), orderRecord.getContactPhone());
        assertEquals(orderRecordEntity.getDeliveryAddress(), orderRecord.getDeliveryAddress());
    }

    @Test
    void shouldMapOrderRecordEntityToOrderRecord() {
        //given
        OrderRecordEntity orderRecordEntity = TestDataForMappers.someOrderRecordEntity();
        //when
        OrderRecord orderRecord = orderRecordEntityMapper.mapFromEntity(orderRecordEntity);
        //then
        assertEquals(orderRecord.getOrderRecordId(), orderRecordEntity.getOrderRecordId());
        assertEquals(orderRecord.getOrderNumber(), orderRecordEntity.getOrderNumber());
        assertEquals(orderRecord.getOrderDateTime(), orderRecordEntity.getOrderDateTime());
        assertEquals(orderRecord.getOrderStatus(), orderRecordEntity.getOrderStatus());
        assertEquals(orderRecord.getOrderStatus(), orderRecordEntity.getOrderStatus());
        assertEquals(orderRecord.getTotalAmount(), orderRecordEntity.getTotalAmount());
        assertEquals(orderRecord.getContactPhone(), orderRecordEntity.getContactPhone());
        assertEquals(orderRecord.getDeliveryAddress(), orderRecordEntity.getDeliveryAddress());
    }
}