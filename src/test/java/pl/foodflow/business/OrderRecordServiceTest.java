package pl.foodflow.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.business.dao.OrderRecordDAO;
import pl.foodflow.business.exceptions.OrderRecordNotFoundException;
import pl.foodflow.domain.*;
import pl.foodflow.enums.OrderStatus;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pl.foodflow.util.TestDataFactory.*;

@ExtendWith(MockitoExtension.class)
class OrderRecordServiceTest {
    @InjectMocks
    private OrderRecordService orderRecordService;
    @Mock
    private OrderRecordDAO orderRecordDAO;
    @Mock
    private CustomerService customerService;
    @Mock
    private OwnerService ownerService;
    @Mock
    private OrderItemService orderItemService;
    @Mock
    private CategoryItemService categoryItemService;

    @Test
    void shouldGetOrderRecordById() {
        //given
        Long orderRecordId = 1L;
        OrderRecord orderRecord = someOrderRecord1();

        when(orderRecordDAO.findOrderRecordById(orderRecordId)).thenReturn(Optional.ofNullable(orderRecord));

        //when
        OrderRecord result = orderRecordService.getOrderRecordById(orderRecordId);

        //then
        assertEquals(result, orderRecord);
        verify(orderRecordDAO).findOrderRecordById(orderRecordId);
    }

    @Test
    void shouldThrownExceptionWhenOrderRecordIdDoesNotExist() {
        //given
        Long orderRecordId = 1L;

        when(orderRecordDAO.findOrderRecordById(orderRecordId)).thenReturn(Optional.empty());

        //when,then
        assertThrows(OrderRecordNotFoundException.class, () -> orderRecordService.getOrderRecordById(orderRecordId));
        verify(orderRecordDAO).findOrderRecordById(orderRecordId);
    }

    @Test
    void shouldFindAllOrderRecords() {
        //given
        var orderRecords = List.of(someOrderRecord1(), someOrderRecord2(), someOrderRecord3());
        when(orderRecordDAO.findAllOrderRecords()).thenReturn(orderRecords);

        //when
        List<OrderRecord> result = orderRecordService.findAll();

        //then
        verify(orderRecordDAO).findAllOrderRecords();
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(3);
    }

    @Test
    void shouldGetAllCustomerOrdersWithStatus() {
        //given
        Integer userId = 1;
        OrderStatus status = OrderStatus.COMPLETED;
        Customer customer = someCustomer1();
        when(customerService.getCustomerByUserId(userId)).thenReturn(customer);

        var orderRecords = List.of(someOrderRecord1(), someOrderRecord2(), someOrderRecord3());
        when(orderRecordDAO.findAllOrderRecords()).thenReturn(orderRecords);
        //when
        List<OrderRecord> result = orderRecordService.getAllCustomerOrdersWithStatus(userId, status);
        //then
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(orderRecord -> status.toString().equals(orderRecord.getOrderStatus())));
    }

    @Test
    void shouldGetAllOwnerOrdersWithStatus() {
        Integer userId = 1;
        OrderStatus status = OrderStatus.IN_PROGRESS;
        Owner owner = someOwner1();
        Restaurant restaurant = someRestaurant1().withOwner(owner);
        when(ownerService.findOwnerByUserId(userId)).thenReturn(owner);

        OrderRecord orderRecord1 = someOrderRecord1().withRestaurant(restaurant);
        OrderRecord orderRecord2 = someOrderRecord2().withRestaurant(restaurant);
        OrderRecord orderRecord3 = someOrderRecord3().withRestaurant(restaurant);

        var orderRecords = List.of(orderRecord1, orderRecord2, orderRecord3);
        when(orderRecordDAO.findAllOrderRecords()).thenReturn(orderRecords);
        //when
        List<OrderRecord> result = orderRecordService.getAllOwnerOrdersWithStatus(userId, status);
        //then
        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(orderRecord -> status.toString().equals(orderRecord.getOrderStatus())));
    }

    @Test
    void shouldSaveOrderRecord() {
        //given
        OrderRecord orderRecord = someOrderRecord2();
        //when
        orderRecordService.saveOrderRecord(orderRecord);
        //then
        verify(orderRecordDAO).saveOrderRecord(orderRecord);
    }

    @Test
    void shouldUpdateOrderRecordSuccessfully() {
        //given
        OrderRecord existingOrderRecord = someOrderRecord1();
        OrderRecord updatedOrderRecord = someUpdatedOrderRecord1();

        when(orderRecordDAO.findOrderRecordById(existingOrderRecord.getOrderRecordId()))
                .thenReturn(Optional.of(existingOrderRecord));
        //when
        orderRecordService.updateOrderRecord(updatedOrderRecord);

        //then
        verify(orderRecordDAO).saveOrderRecord(updatedOrderRecord);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingWithNullId() {
        // Given
        OrderRecord recordWithNullId = someOrderRecord1().withOrderRecordId(null);

        // When/Then
        assertThrows(OrderRecordNotFoundException.class, () -> orderRecordService.updateOrderRecord(recordWithNullId));
    }

    @Test
    void shouldUpdateCategoryItemAndOrderRecordSuccessfully() {
        //given
        CategoryItem categoryItem = someCategoryItem1();
        OrderItem orderItem = someOrderItem1();
        OrderRecord savedOrderRecord = someOrderRecord1();

        when(orderItemService.saveOrderItem(orderItem)).thenReturn(orderItem);
        when(orderRecordDAO.findOrderRecordById(anyLong())).thenReturn(Optional.of(savedOrderRecord));
        //when
        orderRecordService.updateCategoryItemAndOrderRecord(categoryItem, orderItem, savedOrderRecord);
        //then
        verify(orderItemService, times(1)).saveOrderItem(orderItem);
        verify(categoryItemService, times(1))
                .updateCategoryItem(categoryItem.withOrderItems(any()));
        verify(orderRecordDAO, times(1))
                .saveOrderRecord(savedOrderRecord.withOrderItems(any()));
    }

    @Test
    void shouldDeleteOrderWithPermission() {
        //given
        Long orderRecordId = 1L;
        OffsetDateTime orderDateTime = OffsetDateTime.now().minusMinutes(4);
        OrderRecord orderRecordToDelete = someOrderRecord1().withOrderDateTime(orderDateTime);

        when(orderRecordDAO.findOrderRecordById(orderRecordId)).thenReturn(Optional.of(orderRecordToDelete));
        //when
        boolean result = orderRecordService.deleteOrderWithPermission(orderRecordId);
        //then
        assertTrue(result);
        verify(orderItemService, times(1)).deleteOrderItemByOrderRecord(orderRecordToDelete);
        verify(orderRecordDAO, times(1)).deleteOrderRecord(orderRecordToDelete);
    }

    @Test
    void shouldNotDeleteOrderWithPermissionDueToTime() {
        // Given
        Long orderRecordId = 1L;
        OffsetDateTime orderDateTime = OffsetDateTime.now().minusMinutes(6);
        OrderRecord orderRecordToDelete = someOrderRecord1().withOrderDateTime(orderDateTime);

        when(orderRecordDAO.findOrderRecordById(orderRecordId)).thenReturn(Optional.of(orderRecordToDelete));
        //when
        boolean result = orderRecordService.deleteOrderWithPermission(orderRecordId);

        // Then
        assertFalse(result);
        verify(orderItemService, never()).deleteOrderItemByOrderRecord(orderRecordToDelete);
        verify(orderRecordDAO, never()).deleteOrderRecord(orderRecordToDelete);
    }

    @Test
    void changeOrderStatusToCompleted() {
        //given
        OrderRecord orderRecordInProgress = someOrderRecord3();

        when(orderRecordDAO.findOrderRecordById(orderRecordInProgress.getOrderRecordId()))
                .thenReturn(Optional.of(orderRecordInProgress));
        //when
        orderRecordService.changeOrderStatusToCompleted(orderRecordInProgress.getOrderRecordId());
        //then
        verify(orderRecordDAO).saveOrderRecord(argThat(savedOrderRecord ->
                OrderStatus.COMPLETED.toString().equals(savedOrderRecord.getOrderStatus())));
    }
}