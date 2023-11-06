package pl.foodflow.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class RestaurantRequestDTO {
    @Pattern(regexp = "\\d{10}", message = "NIP must be a 10-digit number")
    String nip;

    @NotBlank(message = "Name is required")
    @Size(max = 64, message = "Name must be less than or equal to 64 characters")
    String name;

    @Size(max = 255, message = "Description must be less than or equal to 255 characters")
    String description;

    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$", message = "Invalid time format (HH:mm:ss)")
    String openTime;

    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$", message = "Invalid time format (HH:mm:ss)")
    String closeTime;

    @Pattern(regexp = "\\d{9}", message = "Phone must be a 9-digit number")
    String phone;

    BigDecimal minimumOrderAmount;
    BigDecimal deliveryPrice;
    Boolean deliveryOption;
    AddressRequestDTO address;
    @JsonIgnore
    String ownerEmail;
    @JsonIgnore
    Long ownerId;
}
