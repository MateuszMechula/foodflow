package pl.foodflow.business.dao;

import pl.foodflow.domain.OrderRecord;

import java.util.List;
import java.util.Optional;

public interface OrderRecordDAO {
    Optional<OrderRecord> findOrderRecordById(Long orderRecordId);

    List<OrderRecord> findAllOrderRecords();

    OrderRecord saveOrderRecord(OrderRecord orderRecord);

    void deleteOrderRecord(OrderRecord orderRecord);
}
