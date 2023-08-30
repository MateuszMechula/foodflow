package pl.foodflow.api.dto;


import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
    @Nonnull
    String name;
    @Nonnull
    String description;
    @NotNull
    @DecimalMin(value = "0.01", inclusive = false)
    BigDecimal price;
    @Nonnull
    String imageUrl;
    Long menuCategoryId;
}
