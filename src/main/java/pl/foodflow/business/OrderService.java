package pl.foodflow.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.api.dto.OrderDTO;
import pl.foodflow.domain.*;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderService {
    private final RestaurantService restaurantService;
    private final CategoryItemService categoryItemService;
    private final OrderRecordService orderRecordService;
    public void processAndCreateOrder(Long restaurantId, Customer customer, OrderDTO orderDTO) {
        Restaurant orderProcessingRestaurant = restaurantService.findById(restaurantId);

        OrderRecord orderRecord = buildOrderRecord(customer, orderDTO, orderProcessingRestaurant);
        orderRecordService.save(orderRecord);

        Map<Long, Integer> orderItems = orderDTO.getOrderItems();
        for (Map.Entry<Long, Integer> entry : orderItems.entrySet()) {
            Long categoryItemId = entry.getKey();
            Integer quantity = entry.getValue();
            CategoryItem categoryItem = categoryItemService.findById(categoryItemId);
            OrderItem orderItem = OrderItem.builder()
                    .unitPrice(categoryItem.getPrice())
                    .quantity(quantity)
                    .categoryItem(categoryItem)
                    .orderRecord(orderRecord)
                    .build();

            Set<OrderItem> categoryItemOrderItems = categoryItem.getOrderItems();
            categoryItemOrderItems.add(orderItem);
            categoryItemService.save(categoryItem);
        }

        orderRecordService.save(orderRecord);
    }

    private OrderRecord buildOrderRecord(Customer customer, OrderDTO orderDTO, Restaurant orderProcessingRestaurant) {
        return OrderRecord.builder()
                .orderNumber()
                .orderDateTime(OffsetDateTime.now())
                .orderStatus("ENUM PRZEKAZAĆ W TRAKCIE REALIZACJI")
                .orderNotes(orderDTO.getOrderNotes())
                .contactPhone(orderDTO.getContactPhone())
                .deliveryAddress(orderDTO.getDeliveryAddress())
                .deliveryType(orderDTO.getDeliveryDTO())
                .customer(customer)
                .restaurant(orderProcessingRestaurant)
                .build();
    }
}
