package pl.foodflow.business.dao;

import pl.foodflow.domain.OrderItem;
import pl.foodflow.domain.OrderRecord;

public interface OrderItemDAO {
    OrderItem saveOrderItem(OrderItem orderItem);

    void deleteOrderItemByOrderRecord(OrderRecord orderRecord);
}
