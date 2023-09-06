package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.OrderItemDAO;
import pl.foodflow.business.exceptions.OrdersNotFoundException;
import pl.foodflow.domain.OrderItem;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderItemService {

    private final OrderItemDAO orderItemDAO;

    public List<OrderItem> findOrdersByCategoryItemId(Long categoryItemId) {
        return Optional.of(orderItemDAO.findOrdersByCategoryItemId(categoryItemId))
                .orElseThrow(() -> new OrdersNotFoundException(
                        "Orders for categoryItem ID: [%s] not found".formatted(categoryItemId)));
    }

    @Transactional
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemDAO.saveOrderItem(orderItem);
    }

    @Transactional
    public void deleteOrderItem(OrderItem orderItem) {
        orderItemDAO.deleteOrderItem(orderItem);
    }

    @Transactional
    public void deleteOrderItemByOrderRecordId(Long orderRecordId) {
        orderItemDAO.deleteOrderItemByOrderRecordId(orderRecordId);
    }
}
