package pl.foodflow.business.dao;

import pl.foodflow.domain.OrderRecord;

import java.util.List;
import java.util.Optional;

public interface OrderRecordDAO {
    OrderRecord saveOrderRecord(OrderRecord orderRecord);

    Optional<OrderRecord> findById(Long orderRecordId);

    List<OrderRecord> findAll();

    void delete(OrderRecord orderRecord);
}
