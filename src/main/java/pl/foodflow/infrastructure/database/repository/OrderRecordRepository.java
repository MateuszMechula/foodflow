package pl.foodflow.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.foodflow.business.dao.OrderRecordDAO;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.infrastructure.database.entity.OrderRecordEntity;
import pl.foodflow.infrastructure.database.repository.jpa.OrderRecordJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.OrderRecordEntityMapper;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrderRecordRepository implements OrderRecordDAO {
    private final OrderRecordJpaRepository orderRecordJpaRepository;
    private final OrderRecordEntityMapper orderRecordEntityMapper;

    @Override
    public Optional<OrderRecord> findById(Long orderRecordId) {
        return orderRecordJpaRepository.findById(orderRecordId)
                .map(orderRecordEntityMapper::mapFromEntity);
    }

    @Override
    public List<OrderRecord> findAll() {
        List<OrderRecordEntity> all = orderRecordJpaRepository.findAll();

        return orderRecordJpaRepository.findAll().stream()
                .map(orderRecordEntityMapper::mapFromEntity)
                .toList();
    }


    @Override
    public OrderRecord saveOrderRecord(OrderRecord orderRecord) {
        OrderRecordEntity toSave = orderRecordEntityMapper.mapToEntity(orderRecord);
        OrderRecordEntity saved = orderRecordJpaRepository.save(toSave);
        return orderRecordEntityMapper.mapFromEntity(saved);
    }

    @Override
    public void delete(OrderRecord orderRecord) {
        OrderRecordEntity orderRecordEntity = orderRecordEntityMapper.mapToEntity(orderRecord);
        orderRecordJpaRepository.delete(orderRecordEntity);
    }
}
