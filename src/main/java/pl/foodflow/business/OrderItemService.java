package pl.foodflow.business;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.foodflow.business.dao.OrderItemDAO;
import pl.foodflow.domain.OrderItem;
import pl.foodflow.domain.OrderRecord;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemDAO orderItemDAO;

    @Transactional
    public OrderItem saveOrderItem(OrderItem orderItem) {
        log.info("Saving OrderItem: {}", orderItem);
        return orderItemDAO.saveOrderItem(orderItem);
    }

    @Transactional
    public void deleteOrderItemByOrderRecord(OrderRecord orderRecord) {
        log.info("Deleting OrderItem by OrderRecordId: {}", orderRecord);
        orderItemDAO.deleteOrderItemByOrderRecord(orderRecord);
    }
}
