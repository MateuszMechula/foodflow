package pl.foodflow.integration.support.User;

import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;
import pl.foodflow.infrastructure.security.role.RoleEnum;
import pl.foodflow.infrastructure.security.user.UserDTO;
import pl.foodflow.infrastructure.security.user.UserInformationDTO;

import static pl.foodflow.infrastructure.security.controller.rest.UserRegistrationRestController.*;

public interface UserControllerTestSupport {

    RequestSpecification requestSpecification();

    default UserInformationDTO getUser(final int userId) {
        return requestSpecification()
                .pathParam("userId", userId)
                .get(USERS + USER_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .response()
                .as(UserInformationDTO.class);
    }

    default UserInformationDTO registerUser(final UserDTO userDTO, final RoleEnum role) {
        return requestSpecification()
                .queryParam("role", role.toString())
                .body(userDTO)
                .post(USERS + REGISTER)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .response()
                .as(UserInformationDTO.class);
    }
}
