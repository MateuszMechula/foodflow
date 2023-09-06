package pl.foodflow.business.dao;

import pl.foodflow.domain.OrderItem;

import java.util.List;

public interface OrderItemDAO {

    OrderItem saveOrderItem(OrderItem orderItem);

    void deleteOrderItemByOrderRecordId(Long orderRecordId);

    List<OrderItem> findOrdersByCategoryItemId(Long categoryItemId);

    void deleteOrderItem(OrderItem orderItem);
}
