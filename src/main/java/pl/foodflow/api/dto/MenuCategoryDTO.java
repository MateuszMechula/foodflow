package pl.foodflow.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.foodflow.domain.Restaurant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuCategoryDTO {

    Long menuCategoryId;
    String name;
    String description;
    Restaurant restaurant;

    public static MenuDTO buildDefault() {
        return MenuDTO.builder()
                .name("Lody")
                .description("zajebuste lody")
                .build();
    }
}
