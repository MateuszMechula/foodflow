package pl.foodflow.business;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.OrderRecordDAO;
import pl.foodflow.domain.OrderRecord;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderRecordService {
    private final OrderRecordDAO orderRecordDAO;


    @Transactional
    public OrderRecord saveOrderRecord(OrderRecord orderRecord) {
        return orderRecordDAO.saveOrderRecord(orderRecord);
    }

    @Transactional
    public void updateOrderRecord(OrderRecord orderRecord) {
        if (orderRecord.getOrderRecordId() == null) {
            throw new IllegalArgumentException("OrderRecord ID cannot be NULL");
        }
        OrderRecord existingOrderRecord = orderRecordDAO.findById(orderRecord.getOrderRecordId()).orElseThrow(() ->
                new EntityNotFoundException(
                        "OrderRecord with ID: [%s] doesnt exists".formatted(orderRecord.getOrderRecordId())));

        OrderRecord updatedRecord = buildUpdatedOrderRecord(orderRecord, existingOrderRecord);

        orderRecordDAO.saveOrderRecord(updatedRecord);
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
