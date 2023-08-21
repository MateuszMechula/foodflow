package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.Menu;
import pl.foodflow.infrastructure.database.entity.MenuEntity;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MenuEntityMapper {

    MenuEntity mapToEntity(Menu menu);


    @Mapping(target = "restaurant.address", ignore = true)
    @Mapping(target = "restaurant.owner", ignore = true)
    @Mapping(target = "restaurant.menu", ignore = true)
    @Mapping(target = "menuCategories", ignore = true)
    Menu mapFromEntity(MenuEntity menuEntity);
}
