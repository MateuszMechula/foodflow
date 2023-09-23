package pl.foodflow.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.foodflow.enums.DeliveryType;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @NotEmpty(message = "Order items cannot be empty")
    @Size(min = 1, message = "Order items must have at least one entry")
    @Builder.Default
    private Map<Long, Integer> orderItems = new HashMap<>();

    @NotBlank(message = "Order notes cannot be blank")
    private String orderNotes;

    @NotBlank(message = "Contact phone is required!")
    @Pattern(regexp = "\\d{9}", message = "Contact phone must be a 9-digit number")
    private String contactPhone;

    private String deliveryAddress;
    private DeliveryType deliveryType;
}
