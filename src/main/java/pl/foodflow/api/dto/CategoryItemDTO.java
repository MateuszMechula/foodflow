package pl.foodflow.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    String imageUrl;
    Long menuCategoryId;
}
