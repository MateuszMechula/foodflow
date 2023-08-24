package pl.foodflow.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.foodflow.api.dto.CategoryItemDTO;
import pl.foodflow.domain.CategoryItem;

@Mapper(componentModel = "spring")
public interface CategoryItemMapper {

    CategoryItem map(CategoryItemDTO entity);
}
