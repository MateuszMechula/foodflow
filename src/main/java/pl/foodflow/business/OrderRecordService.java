package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.OrderRecordDAO;
import pl.foodflow.business.exceptions.OrderRecordNotFoundException;
import pl.foodflow.domain.*;
import pl.foodflow.enums.OrderStatus;
import pl.foodflow.utils.ErrorMessages;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderRecordService {

    private final OrderRecordDAO orderRecordDAO;
    private final CustomerService customerService;
    private final CategoryItemService categoryItemService;
    private final OrderItemService orderItemService;
    private final OwnerService ownerService;

    public OrderRecord getOrderRecordById(Long orderRecordId) {
        log.info("Fetching OrderRecord by OrderRecordID: {}", orderRecordId);
        return orderRecordDAO.findOrderRecordById(orderRecordId)
                .orElseThrow(() -> new OrderRecordNotFoundException(
                        ErrorMessages.ORDER_RECORD_NOT_FOUND.formatted(orderRecordId)));
    }

    public List<OrderRecord> findAll() {
        log.info("Fetching all OrderRecords");
        return orderRecordDAO.findAllOrderRecords();
    }

    public List<OrderRecord> getAllCustomerOrdersWithStatus(Integer userId, OrderStatus status) {
        log.info("Fetching all orders for customer with user ID: {} with status: {}", userId, status);
        Customer customer = customerService.getCustomerByUserId(userId);
        return filterOrdersByCustomerAndStatus(customer, status);
    }

    public List<OrderRecord> getAllOwnerOrdersWithStatus(Integer userId, OrderStatus status) {
        log.info("Fetching all orders for owner with user ID: {} with status: {}", userId, status);
        Owner owner = ownerService.findOwnerByUserId(userId);
        return filterOrdersByOwnerAndStatus(owner, status);
    }

    @Transactional
    public OrderRecord saveOrderRecord(OrderRecord orderRecord) {
        log.info("Saving order");
        return orderRecordDAO.saveOrderRecord(orderRecord);
    }

    @Transactional
    public void updateOrderRecord(OrderRecord orderRecord) {
        if (orderRecord.getOrderRecordId() == null) {
            throw new OrderRecordNotFoundException(ErrorMessages.ORDER_RECORD_ID_IS_NULL);
        }
        log.info("Updating OrderRecord with ID: {}", orderRecord.getOrderRecordId());
        OrderRecord existingOrderRecord = getOrderRecordById(orderRecord.getOrderRecordId());
        OrderRecord updatedRecord = buildUpdatedOrderRecord(orderRecord, existingOrderRecord);

        saveOrderRecord(updatedRecord);
    }

    @Transactional
    public void updateCategoryItemAndOrderRecord(
            CategoryItem categoryItem,
            OrderItem orderItem,
            OrderRecord savedOrderRecord) {

        log.info("Updating category and order");

        OrderItem savedOrderItem = orderItemService.saveOrderItem(orderItem);
        Set<OrderItem> categoryItemOrderItems = categoryItem.getOrderItems();
        categoryItemOrderItems.add(savedOrderItem);

        Set<OrderItem> orderRecordItems = savedOrderRecord.getOrderItems();
        orderRecordItems.add(savedOrderItem);

        categoryItemService.updateCategoryItem(categoryItem.withOrderItems(categoryItemOrderItems));
        updateOrderRecord(savedOrderRecord.withOrderItems(orderRecordItems));
    }

    @Transactional
    public boolean deleteOrderWithPermission(Long orderRecordId) throws OrderRecordNotFoundException {
        log.info("Deleting order with ID: {}", orderRecordId);
        OrderRecord orderRecordToDelete = getOrderRecordById(orderRecordId);

        LocalDateTime orderDateTime = orderRecordToDelete.getOrderDateTime().toLocalDateTime();
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);

        if (orderDateTime.isBefore(fiveMinutesAgo)) {
            return false;
        }
        orderItemService.deleteOrderItemByOrderRecord(orderRecordToDelete);
        orderRecordDAO.deleteOrderRecord(orderRecordToDelete);
        return true;
    }

    @Transactional
    public void changeOrderStatusToCompleted(Long orderRecordId) {
        log.info("Changing order status to COMPLETED for order with ID: {}", orderRecordId);
        OrderRecord orderRecord = getOrderRecordById(orderRecordId);
        saveOrderRecord(orderRecord.withOrderStatus(String.valueOf(OrderStatus.COMPLETED)));
    }

    private List<OrderRecord> filterOrdersByOwnerAndStatus(Owner owner, OrderStatus status) {
        log.info("Filtering orders for owner with status: {}", status);
        return findAll().stream()
                .filter(orderRecord -> orderRecord.getRestaurant().getOwner().equals(owner))
                .filter(orderRecord -> status.toString().equals(orderRecord.getOrderStatus()))
                .collect(Collectors.toList());
    }

    private List<OrderRecord> filterOrdersByCustomerAndStatus(Customer customer, OrderStatus status) {
        log.info("Filtering orders for customer with status: {}", status);
        return findAll().stream()
                .filter(orderRecord -> orderRecord.getCustomer().equals(customer))
                .filter(orderRecord -> status.toString().equals(orderRecord.getOrderStatus()))
                .collect(Collectors.toList());
    }

    private static OrderRecord buildUpdatedOrderRecord(OrderRecord orderRecord, OrderRecord existingOrderRecord) {
        return OrderRecord.builder()
                .orderRecordId(existingOrderRecord.getOrderRecordId())
                .orderNumber(Optional.ofNullable(orderRecord.getOrderNumber()).orElse(existingOrderRecord.getOrderNumber()))
                .orderDateTime(Optional.ofNullable(orderRecord.getOrderDateTime()).orElse(existingOrderRecord.getOrderDateTime()))
                .orderStatus(Optional.ofNullable(orderRecord.getOrderStatus()).orElse(existingOrderRecord.getOrderStatus()))
                .orderNotes(Optional.ofNullable(orderRecord.getOrderNotes()).orElse(existingOrderRecord.getOrderNotes()))
                .contactPhone(Optional.ofNullable(orderRecord.getContactPhone()).orElse(existingOrderRecord.getContactPhone()))
                .deliveryAddress(Optional.ofNullable(orderRecord.getDeliveryAddress()).orElse(existingOrderRecord.getDeliveryAddress()))
                .deliveryType(Optional.ofNullable(orderRecord.getDeliveryType()).orElse(existingOrderRecord.getDeliveryType()))
                .customer(Optional.ofNullable(orderRecord.getCustomer()).orElse(existingOrderRecord.getCustomer()))
                .restaurant(Optional.ofNullable(orderRecord.getRestaurant()).orElse(existingOrderRecord.getRestaurant()))
                .orderItems(Optional.ofNullable(orderRecord.getOrderItems()).orElse(existingOrderRecord.getOrderItems()))
                .build();
    }
}
