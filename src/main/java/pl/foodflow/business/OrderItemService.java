package pl.foodflow.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.OrderItemDAO;
import pl.foodflow.domain.OrderItem;

@Service
@AllArgsConstructor
public class OrderItemService {

    private final OrderItemDAO orderItemDAO;

    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemDAO.saveOrderItem(orderItem);
    }
}
