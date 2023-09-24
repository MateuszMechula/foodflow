package pl.foodflow.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.business.dao.OrderItemDAO;
import pl.foodflow.domain.OrderItem;
import pl.foodflow.domain.OrderRecord;

import static org.mockito.Mockito.verify;
import static pl.foodflow.util.TestDataFactory.someOrderItem1;
import static pl.foodflow.util.TestDataFactory.someOrderRecord1;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {

    @InjectMocks
    private OrderItemService orderItemService;
    @Mock
    private OrderItemDAO orderItemDAO;

    @Test
    void shouldSaveOrderItem() {
        //given
        OrderItem orderItem = someOrderItem1();
        //when
        orderItemService.saveOrderItem(orderItem);
        //then
        verify(orderItemDAO).saveOrderItem(orderItem);
    }

    @Test
    void shouldDeleteOrderItemByOrderRecord() {
        //given
        OrderRecord orderRecord = someOrderRecord1();
        //when
        orderItemService.deleteOrderItemByOrderRecord(orderRecord);
        //then
        verify(orderItemDAO).deleteOrderItemByOrderRecord(orderRecord);
    }
}