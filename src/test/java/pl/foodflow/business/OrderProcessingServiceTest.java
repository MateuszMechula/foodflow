package pl.foodflow.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.api.dto.OrderDTO;
import pl.foodflow.business.exceptions.OrderItemsNotFoundException;
import pl.foodflow.domain.CategoryItem;
import pl.foodflow.domain.Customer;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.domain.Restaurant;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static pl.foodflow.util.TestDataFactory.*;

@ExtendWith(MockitoExtension.class)
class OrderProcessingServiceTest {
    @InjectMocks
    private OrderProcessingService orderProcessingService;
    @Mock
    private OrderRecordService orderRecordService;
    @Mock
    private RestaurantService restaurantService;
    @Mock
    private CategoryItemService categoryItemService;

    @Test
    void shouldProcessAndCreateOrder() {
        //given
        Long restaurantId = 1L;
        OrderDTO orderDTO = someOrderDTO();
        Restaurant restaurant = someRestaurant1();
        OrderRecord orderRecord = someOrderRecord1();
        CategoryItem categoryItem = someCategoryItem1();
        Customer customer = someCustomer1();

        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(restaurant);
        when(orderRecordService.saveOrderRecord(any(OrderRecord.class))).thenReturn(orderRecord);
        when(categoryItemService.getCategoryItemById(1L)).thenReturn(categoryItem);
        //when
        OrderRecord result = orderProcessingService.processAndCreateOrder(restaurantId, customer, orderDTO);
        //then
        assertNotNull(result);
        assertEquals(orderRecord, result);
    }

    @Test
    void shouldThrownExceptionWhenOrderIsEmpty() {
        //given
        Long restaurantId = 1L;
        Customer customer = someCustomer1();
        OrderDTO orderDTO = someEmptyOrderDTO();

        Restaurant restaurant = someRestaurant1();
        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(restaurant);
        //when,then
        assertThrows(OrderItemsNotFoundException.class,
                () -> orderProcessingService.processAndCreateOrder(restaurantId, customer, orderDTO));
    }
}