package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.OrderRecordDAO;
import pl.foodflow.business.exceptions.OrderRecordNotFoundException;
import pl.foodflow.domain.*;
import pl.foodflow.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderRecordService {

    private final OrderRecordDAO orderRecordDAO;
    private final CustomerService customerService;
    private final CategoryItemService categoryItemService;
    private final OrderItemService orderItemService;
    private final OwnerService ownerService;

    @Transactional
    public OrderRecord findById(Long orderRecordId) {
        return orderRecordDAO.findById(orderRecordId)
                .orElseThrow(() -> new OrderRecordNotFoundException(
                        "Order Record with ID: [%s] not found".formatted(orderRecordId)));
    }

    @Transactional
    public List<OrderRecord> findAll() {
        return orderRecordDAO.findAll();
    }

    @Transactional
    public List<OrderRecord> getAllCustomerOrdersWithOrderStatusInProgress(long userId) {
        Customer customer = customerService.findByUserId(userId);

        return findAll().stream()
                .filter(orderRecord -> orderRecord.getCustomer().getCustomerId().equals(customer.getCustomerId()))
                .filter(orderRecord -> OrderStatus.IN_PROGRESS.toString().equals(orderRecord.getOrderStatus()))
                .toList();
    }

    @Transactional
    public List<OrderRecord> getAllCustomerOrdersWithOrderStatusCompleted(long userId) {
        Customer customer = customerService.findByUserId(userId);

        return findAll().stream()
                .filter(orderRecord -> orderRecord.getCustomer().getCustomerId().equals(customer.getCustomerId()))
                .filter(orderRecord -> OrderStatus.COMPLETED.toString().equals(orderRecord.getOrderStatus()))
                .toList();
    }

    @Transactional
    public List<OrderRecord> getAllOwnerOrdersWithOrderStatusInProgress(long userId) {
        Owner owner = ownerService.findByUserId((int) userId);

        return findAll().stream()
                .filter(orderRecord ->
                        orderRecord.getRestaurant().getOwner().getOwnerId().equals(owner.getOwnerId()))
                .filter(orderRecord -> OrderStatus.IN_PROGRESS.toString().equals(orderRecord.getOrderStatus()))
                .toList();
    }

    @Transactional
    public List<OrderRecord> getAllOwnerOrdersWithOrderStatusCompleted(long userId) {

        Owner owner = ownerService.findByUserId((int) userId);

        return findAll().stream()
                .filter(orderRecord ->
                        orderRecord.getRestaurant().getOwner().getOwnerId().equals(owner.getOwnerId()))
                .filter(orderRecord -> OrderStatus.COMPLETED.toString().equals(orderRecord.getOrderStatus()))
                .toList();
    }

    @Transactional
    public void changeOrderStatusToCompleted(Long orderRecordId) {
        OrderRecord orderRecord = findById(orderRecordId);
        saveOrderRecord(orderRecord.withOrderStatus(String.valueOf(OrderStatus.COMPLETED)));
    }

    @Transactional
    public OrderRecord saveOrderRecord(OrderRecord orderRecord) {
        return orderRecordDAO.saveOrderRecord(orderRecord);
    }

    @Transactional
    public void updateOrderRecord(OrderRecord orderRecord) {
        if (orderRecord.getOrderRecordId() == null) {
            throw new IllegalArgumentException("OrderRecord ID cannot be NULL");
        }
        OrderRecord existingOrderRecord = findById(orderRecord.getOrderRecordId());
        OrderRecord updatedRecord = buildUpdatedOrderRecord(orderRecord, existingOrderRecord);

        saveOrderRecord(updatedRecord);
    }

    @Transactional
    public boolean deleteOrderWithPermission(Long orderRecordId) throws OrderRecordNotFoundException {
        OrderRecord orderRecord = findById(orderRecordId);

        LocalDateTime orderDateTime = orderRecord.getOrderDateTime().toLocalDateTime();
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);

        if (orderDateTime.isBefore(fiveMinutesAgo)) {
            return false;
        }
        orderItemService.deleteOrderItemByOrderRecordId(orderRecordId);
        orderRecordDAO.delete(orderRecord);
        return true;
    }

    @Transactional
    public void updateCategoryItemAndOrderRecord(
            CategoryItem categoryItem,
            OrderItem orderItem,
            OrderRecord savedOrderRecord) {
        OrderItem savedOrderItem = orderItemService.saveOrderItem(orderItem);

        Set<OrderItem> categoryItemOrderItems = categoryItem.getOrderItems();
        categoryItemOrderItems.add(savedOrderItem);

        Set<OrderItem> orderRecordItems = savedOrderRecord.getOrderItems();
        orderRecordItems.add(savedOrderItem);

        categoryItemService.updateCategoryItem(categoryItem.withOrderItems(categoryItemOrderItems));
        updateOrderRecord(savedOrderRecord.withOrderItems(orderRecordItems));
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
