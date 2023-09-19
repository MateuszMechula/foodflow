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
public class SearchAddressDTO {

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "Postal code is required")
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Postal code should be in the format XX-XXX")
    private String postalCode;

    @NotBlank(message = "City is required")
    private String city;

}
