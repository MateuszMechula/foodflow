package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.api.dto.OrderDTO;
import pl.foodflow.business.exceptions.OrderItemsNotFoundException;
import pl.foodflow.domain.*;
import pl.foodflow.enums.OrderStatus;
import pl.foodflow.utils.ErrorMessages;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProcessingService {
    public static final String ORDER_PREFIX = "ORDER";

    private final OrderRecordService orderRecordService;
    private final RestaurantService restaurantService;
    private final CategoryItemService categoryItemService;

    @Transactional
    public OrderRecord processAndCreateOrder(Long restaurantId, Customer customer, OrderDTO orderDTO) {
        log.debug("Processing order for restaurantID: {}, customer: {}", restaurantId, customer);
        Restaurant orderProcessingRestaurant = restaurantService.getRestaurantById(restaurantId);
        OrderRecord orderRecord = createOrderRecord(customer, orderDTO, orderProcessingRestaurant);
        OrderRecord savedOrderRecord = orderRecordService.saveOrderRecord(orderRecord);

        Map<Long, Integer> orderItems = orderDTO.getOrderItems();
        orderItems.entrySet().removeIf(entry -> entry.getValue() == null);
        validateOrderItems(orderItems);

        for (Map.Entry<Long, Integer> entry : orderItems.entrySet()) {
            Long categoryItemId = entry.getKey();
            Integer quantity = entry.getValue();
            CategoryItem categoryItem = categoryItemService.getCategoryItemById(categoryItemId);
            OrderItem orderItem = createOrderItem(categoryItem, quantity, savedOrderRecord);
            orderRecordService.updateCategoryItemAndOrderRecord(categoryItem, orderItem, savedOrderRecord);
        }

        BigDecimal totalAmount = calculateTotalAmount(savedOrderRecord);
        orderRecordService.saveOrderRecord(savedOrderRecord.withTotalAmount(totalAmount));
        log.info("Order created successfully");
        return savedOrderRecord.withTotalAmount(totalAmount);
    }

    private void validateOrderItems(Map<Long, Integer> orderItems) {
        if (orderItems.isEmpty()) {
            throw new OrderItemsNotFoundException(ErrorMessages.ORDER_ITEMS_NOT_FOUND);
        }
    }

    private String generateOrderNumber(Restaurant restaurant) {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(now);
        return ORDER_PREFIX + restaurant.getRestaurantId() + "-" + timestamp;
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

    private OrderRecord createOrderRecord(
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
