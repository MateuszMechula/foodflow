package pl.foodflow.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.foodflow.api.dto.OrderItemDTO;
import pl.foodflow.domain.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "categoryItem.categoryItemId", target = "categoryItemId")
    OrderItemDTO mapToDTO(OrderItem orderItem);
}
