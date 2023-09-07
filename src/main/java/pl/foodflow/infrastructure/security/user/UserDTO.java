package pl.foodflow.infrastructure.security.user;

import lombok.Data;

@Data
public class UserDTO {

    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String street;
    private String postalCode;
    private String city;
    private String country;
    private String role;
}
