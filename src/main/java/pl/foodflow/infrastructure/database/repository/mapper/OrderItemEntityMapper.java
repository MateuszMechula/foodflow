package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.OrderItem;
import pl.foodflow.infrastructure.database.entity.OrderItemEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderItemEntityMapper {
    OrderItemEntity mapToEntity(OrderItem orderItem);

    @Mapping(target = "categoryItem", ignore = true)
    @Mapping(target = "orderRecord", ignore = true)
    OrderItem mapFromEntity(OrderItemEntity saved);
}
