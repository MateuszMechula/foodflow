package pl.foodflow.business.dao;

import pl.foodflow.domain.OrderItem;

public interface OrderItemDAO {

    OrderItem saveOrderItem(OrderItem orderItem);
}
