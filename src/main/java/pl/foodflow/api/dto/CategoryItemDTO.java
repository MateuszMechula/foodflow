package pl.foodflow.api.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Name is required")
    String name;

    @Size(max = 255, message = "Description must be less than or equal to 255 characters")
    String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", inclusive = false)
    BigDecimal price;

    String imageUrl;

    Long menuCategoryId;
}
