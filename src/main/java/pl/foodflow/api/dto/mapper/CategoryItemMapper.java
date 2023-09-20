package pl.foodflow.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.foodflow.api.dto.CategoryItemDTO;
import pl.foodflow.domain.CategoryItem;

@Mapper(componentModel = "spring")
public interface CategoryItemMapper {
    @Mapping(target = "menuCategory", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    CategoryItem map(CategoryItemDTO entity);
}
