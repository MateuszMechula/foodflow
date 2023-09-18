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

    @NotBlank(message = "Street cannot be blank")
    private String street;
    @NotBlank(message = "Postal code cannot be blank")
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Postal code should be in the format XX-XXX")
    private String postalCode;
    @NotBlank(message = "City cannot be blank")
    private String city;

}
