package pl.foodflow.integration.rest.customer;

import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.integration.configuration.RestAssuredIntegrationTestBase;
import pl.foodflow.integration.support.customer.CustomerSearchRestaurantRestControllerTestSupport;

@TestPropertySource(properties = "test.name=CustomerSearchRestaurantRestAssuredIT")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerSearchRestaurantControllerRestAssuredIT
        extends RestAssuredIntegrationTestBase
        implements CustomerSearchRestaurantRestControllerTestSupport {

    @Test
    void shouldSearchRestaurantsByAddress() {
        //given
        String street = "Klonowa";
        String postalCode = "11-400";
        String city = "Kętrzyn";

        //when
        Response response = searchRestaurants(street, postalCode, city);
        //then
        response.then().statusCode(HttpStatus.OK.value());
    }
}
