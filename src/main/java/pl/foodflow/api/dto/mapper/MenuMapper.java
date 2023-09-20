package pl.foodflow.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.foodflow.api.dto.MenuDTO;
import pl.foodflow.domain.Menu;

@Mapper(componentModel = "spring")
public interface MenuMapper {
    @Mapping(target = "menuCategories", ignore = true)
    Menu map(MenuDTO menuDTO);
}
