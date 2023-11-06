package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.OrderItem;
import pl.foodflow.infrastructure.database.entity.OrderItemEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderItemEntityMapper {
    OrderItemEntity mapToEntity(OrderItem orderItem);

    @Mapping(target = "categoryItem.name", ignore = true)
    @Mapping(target = "categoryItem.description", ignore = true)
    @Mapping(target = "categoryItem.price", ignore = true)
    @Mapping(target = "categoryItem.imageUrl", ignore = true)
    @Mapping(target = "categoryItem.menuCategory", ignore = true)
    @Mapping(target = "categoryItem.orderItems", ignore = true)
    @Mapping(target = "orderRecord", ignore = true)
    OrderItem mapFromEntity(OrderItemEntity saved);
}
