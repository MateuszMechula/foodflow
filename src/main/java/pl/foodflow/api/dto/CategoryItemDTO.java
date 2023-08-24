package pl.foodflow.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.foodflow.domain.ItemImage;
import pl.foodflow.domain.MenuCategory;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryItemDTO {

    Long categoryItemId;
    String name;
    String description;
    BigDecimal price;
    Long menuCategoryId;
    ItemImage itemImage;
}
