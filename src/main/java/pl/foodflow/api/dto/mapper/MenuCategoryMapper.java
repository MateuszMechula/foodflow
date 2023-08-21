package pl.foodflow.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.foodflow.api.dto.MenuCategoryDTO;
import pl.foodflow.domain.MenuCategory;

@Mapper(componentModel = "spring")
public interface MenuCategoryMapper {
    MenuCategory map(MenuCategoryDTO menuCategoryDTO);
}
