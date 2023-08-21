package pl.foodflow.api.dto.mapper;

import org.mapstruct.Mapper;
import pl.foodflow.api.dto.MenuDTO;
import pl.foodflow.domain.Menu;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    Menu map(MenuDTO menuDTO);
}
