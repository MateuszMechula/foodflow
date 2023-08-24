package pl.foodflow.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.foodflow.api.dto.MenuCategoryDTO;
import pl.foodflow.domain.MenuCategory;

@Mapper(componentModel = "spring")
public interface MenuCategoryMapper {

    @Mapping(target = "categoryItems", ignore = true)
    @Mapping(target = "menu", ignore = true)
    MenuCategory map(MenuCategoryDTO menuCategoryDTO);
}
