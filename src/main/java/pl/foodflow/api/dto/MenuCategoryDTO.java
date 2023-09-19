package pl.foodflow.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Name is required")
    String name;

    @Size(max = 255, message = "Description must be less than or equal to 255 characters")
    String description;

    Restaurant restaurant;

    public static MenuDTO buildDefault() {
        return MenuDTO.builder()
                .name("Lody")
                .description("zajebuste lody")
                .build();
    }
}
