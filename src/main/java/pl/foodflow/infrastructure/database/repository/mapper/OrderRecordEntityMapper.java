package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.infrastructure.database.entity.OrderRecordEntity;

@Mapper(uses = {RestaurantEntityMapper.class, CustomerEntityMapper.class, OrderItemEntityMapper.class},
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderRecordEntityMapper {

    OrderRecord mapFromEntity(OrderRecordEntity entity);

    OrderRecordEntity mapToEntity(OrderRecord orderRecord);
}
