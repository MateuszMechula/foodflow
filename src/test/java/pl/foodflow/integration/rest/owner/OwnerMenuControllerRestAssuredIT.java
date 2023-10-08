package pl.foodflow.integration.rest.owner;

import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.api.dto.MenuDTO;
import pl.foodflow.infrastructure.database.repository.jpa.MenuJpaRepository;
import pl.foodflow.integration.configuration.RestAssuredIntegrationTestBase;
import pl.foodflow.integration.support.owner.OwnerMenuRestControllerTestSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.foodflow.util.TestDataFactory.someMenuDTO2;

@TestPropertySource(properties = "test.name=OwnerMenuControllerRestAssuredIT")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OwnerMenuControllerRestAssuredIT
        extends RestAssuredIntegrationTestBase
        implements OwnerMenuRestControllerTestSupport {

    private MenuJpaRepository menuJpaRepository;

    @Test
    void shouldAddMenu() {
        //given
        menuJpaRepository.deleteAll();
        MenuDTO menuDTO = someMenuDTO2();
        Long ownerId = 1L;
        //when
        Response response = addMenu(menuDTO, ownerId);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void shouldDeleteMenu() {
        //given
        Long menuId = 1L;
        //when
        Response response = deleteMenu(menuId);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
