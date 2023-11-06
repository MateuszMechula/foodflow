package pl.foodflow.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.foodflow.api.dto.OrderRecordDTO;
import pl.foodflow.domain.OrderRecord;

@Mapper(uses = OrderItemMapper.class, componentModel = "spring")
public interface OrderRecordMapper {

    @Mapping(source = "customer.customerId", target = "customerId")
    @Mapping(source = "restaurant.restaurantId", target = "restaurantId")
    OrderRecordDTO mapToDTO(OrderRecord orderRecord);
}
