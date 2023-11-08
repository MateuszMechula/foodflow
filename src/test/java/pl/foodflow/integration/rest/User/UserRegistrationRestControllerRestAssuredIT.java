package pl.foodflow.integration.rest.User;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.infrastructure.security.role.RoleEnum;
import pl.foodflow.infrastructure.security.user.UserDTO;
import pl.foodflow.infrastructure.security.user.UserInformationDTO;
import pl.foodflow.integration.configuration.RestAssuredIntegrationTestBase;
import pl.foodflow.integration.support.User.UserControllerTestSupport;
import pl.foodflow.util.TestDataFactory;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(properties = "test.name=UserRegistrationRestControllerTest")
@AllArgsConstructor(onConstructor = @__(@Autowired))
class UserRegistrationRestControllerRestAssuredIT
        extends RestAssuredIntegrationTestBase
        implements UserControllerTestSupport {

    @Test
    void shouldGetUserCorrectly() {
        //given
        int userId = 1;

        //when
        UserInformationDTO user = getUser(userId);

        //then
        assertThat(user).isNotNull();
        assertThat(user.getUserName()).isEqualTo("test_owner");
        assertThat(user.getUserId()).isEqualTo(1);
    }

    @Test
    void shouldRegisterUserCorrectly() {
        //given
        UserDTO userDTO = TestDataFactory.someUserDTO1();
        RoleEnum role = RoleEnum.OWNER;

        //when
        UserInformationDTO userInformationDTO = registerUser(userDTO, role);

        //then
        assertThat(userInformationDTO).isNotNull();
        assertThat(userDTO.getUsername()).isEqualTo(userInformationDTO.getUserName());
    }
}