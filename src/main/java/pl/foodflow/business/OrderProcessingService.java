package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.api.dto.OrderDTO;
import pl.foodflow.domain.*;
import pl.foodflow.enums.OrderStatus;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderProcessingService {
    private final RestaurantService restaurantService;
    private final CategoryItemService categoryItemService;
    private final OrderRecordService orderRecordService;
    private final OrderItemService orderItemService;

    @Transactional
    public void processAndCreateOrder(Long restaurantId, Customer customer, OrderDTO orderDTO) {
        Restaurant orderProcessingRestaurant = restaurantService.findById(restaurantId);

        OrderRecord orderRecord = buildOrderRecord(customer, orderDTO, orderProcessingRestaurant);
        OrderRecord savedOrderRecord = orderRecordService.saveOrderRecord(orderRecord);

        Map<Long, Integer> orderItems = orderDTO.getOrderItems();
        for (Map.Entry<Long, Integer> entry : orderItems.entrySet()) {
            Long categoryItemId = entry.getKey();
            Integer quantity = entry.getValue();
            CategoryItem categoryItem = categoryItemService.findById(categoryItemId);
            OrderItem orderItem = OrderItem.builder()
                    .unitPrice(categoryItem.getPrice())
                    .quantity(quantity)
                    .categoryItem(categoryItem)
                    .orderRecord(savedOrderRecord)
                    .build();

            OrderItem savedOrderItem = orderItemService.saveOrderItem(orderItem);

            Set<OrderItem> categoryItemOrderItems = categoryItem.getOrderItems();
            categoryItemOrderItems.add(savedOrderItem);
            Set<OrderItem> orderRecordItems = savedOrderRecord.getOrderItems();
            orderRecordItems.add(savedOrderItem);

            categoryItemService.updateCategoryItem(categoryItem.withOrderItems(categoryItemOrderItems));
            orderRecordService.updateOrderRecord(savedOrderRecord.withOrderItems(orderRecordItems));
        }

    }

    private String generateOrderNumber(Restaurant restaurant) {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(now);
        return "ORDER-" + restaurant.getRestaurantId() + "-" + timestamp;
    }

    private OrderRecord buildOrderRecord(Customer customer, OrderDTO orderDTO, Restaurant orderProcessingRestaurant) {
        return OrderRecord.builder()
                .orderNumber(generateOrderNumber(orderProcessingRestaurant))
                .orderDateTime(OffsetDateTime.now())
                .orderStatus(String.valueOf(OrderStatus.IN_PROGRESS))
                .orderNotes(orderDTO.getOrderNotes())
                .contactPhone(orderDTO.getContactPhone())
                .deliveryAddress(orderDTO.getDeliveryAddress())
                .deliveryType(String.valueOf(orderDTO.getDeliveryType()))
                .customer(customer)
                .restaurant(orderProcessingRestaurant)
                .build();
    }
}
