package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.api.dto.OrderDTO;
import pl.foodflow.business.dao.CategoryItemDAO;
import pl.foodflow.business.dao.RestaurantDAO;
import pl.foodflow.business.exceptions.CategoryItemNotFoundException;
import pl.foodflow.business.exceptions.OrderItemsNotFoundException;
import pl.foodflow.business.exceptions.RestaurantNotFound;
import pl.foodflow.domain.*;
import pl.foodflow.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@AllArgsConstructor
public class OrderProcessingService {
    private final RestaurantDAO restaurantDAO;
    private final CategoryItemDAO categoryItemDAO;
    private final OrderRecordService orderRecordService;

    @Transactional
    public OrderRecord processAndCreateOrder(Long restaurantId, Customer customer, OrderDTO orderDTO) {
        Restaurant orderProcessingRestaurant = restaurantDAO.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFound(
                        "Restaurant with ID: [%s] not found".formatted(restaurantId)));

        OrderRecord orderRecord = buildOrderRecord(customer, orderDTO, orderProcessingRestaurant);
        OrderRecord savedOrderRecord = orderRecordService.saveOrderRecord(orderRecord);

        Map<Long, Integer> orderItems = orderDTO.getOrderItems();
        orderItems.entrySet().removeIf(entry -> entry.getValue() == null);

        validateOrderItems(orderItems);

        for (Map.Entry<Long, Integer> entry : orderItems.entrySet()) {
            Long categoryItemId = entry.getKey();
            Integer quantity = entry.getValue();
            CategoryItem categoryItem = categoryItemDAO.findById(categoryItemId)
                    .orElseThrow(() -> new CategoryItemNotFoundException(
                            "CategoryItem with ID: [%s] not found".formatted(categoryItemId)));

            OrderItem orderItem = createOrderItem(categoryItem, quantity, savedOrderRecord);
            orderRecordService.updateCategoryItemAndOrderRecord(categoryItem, orderItem, savedOrderRecord);
        }

        BigDecimal totalAmount = calculateTotalAmount(savedOrderRecord);
        orderRecordService.saveOrderRecord(savedOrderRecord.withTotalAmount(totalAmount));
        return savedOrderRecord.withTotalAmount(totalAmount);
    }

    private void validateOrderItems(Map<Long, Integer> orderItems) {
        if (orderItems.isEmpty()) {
            throw new OrderItemsNotFoundException("OrderItems not found!");
        }
    }

    private String generateOrderNumber(Restaurant restaurant) {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(now);
        return "ORDER-" + restaurant.getRestaurantId() + "-" + timestamp;
    }

    private BigDecimal calculateTotalAmount(OrderRecord orderRecord) {
        return orderRecord.getOrderItems().stream()
                .map(orderItem -> orderItem.getUnitPrice()
                        .multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static OrderItem createOrderItem(
            CategoryItem categoryItem,
            Integer quantity,
            OrderRecord savedOrderRecord) {
        return OrderItem.builder()
                .unitPrice(categoryItem.getPrice())
                .quantity(quantity)
                .categoryItem(categoryItem)
                .orderRecord(savedOrderRecord)
                .build();
    }

    private OrderRecord buildOrderRecord(
            Customer customer,
            OrderDTO orderDTO,
            Restaurant orderProcessingRestaurant) {
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
