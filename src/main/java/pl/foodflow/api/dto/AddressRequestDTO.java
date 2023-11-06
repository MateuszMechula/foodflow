package pl.foodflow.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDTO {

    @NotBlank(message = "Street is required")
    String street;

    @NotBlank(message = "Postal code is required")
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Postal code should be in the format XX-XXX")
    String postalCode;

    @NotBlank(message = "City is required")
    String city;

    @NotBlank(message = "Country is required")
    String country;
}
