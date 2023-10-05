package pl.foodflow.integration.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import pl.foodflow.integration.support.AuthenticationTestSupport;
import pl.foodflow.integration.support.ControllerTestSupport;

import static pl.foodflow.api.controller.customer.CustomerHomePageController.CUSTOMER;
import static pl.foodflow.api.controller.owner.OwnerRestaurantController.OWNER;

public abstract class RestAssuredIntegrationTestBase
        extends AbstractIntegrationTest
        implements ControllerTestSupport, AuthenticationTestSupport {
    @Autowired
    private ObjectMapper objectMapper;
    private String jSessionIdValue;

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public RequestSpecification restAssuredBase() {
        return RestAssured
                .given()
                .config(getConfig())
                .basePath(basePath)
                .port(serverPort);
    }

    private RestAssuredConfig getConfig() {
        return RestAssuredConfig.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> objectMapper));
    }

    public RequestSpecification requestSpecification() {
        return restAssuredBase()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .cookie("JSESSIONID", jSessionIdValue);
    }

    public RequestSpecification requestSpecificationNoAuthentication() {
        return restAssuredBase();
    }

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        if (testInfo.getTestMethod().isPresent()) {
            if (testInfo.getTestMethod().get().isAnnotationPresent(Tag.class)) {
                Tag tag = testInfo.getTestMethod().get().getAnnotation(Tag.class);
                String tagValue = tag.value();
                if ("customer".equals(tagValue)) {
                    jSessionIdValue = login("test_customer", "test")
                            .and()
                            .cookie("JSESSIONID")
                            .header(HttpHeaders.LOCATION, "http://localhost:%s%s%s".formatted(serverPort, basePath, CUSTOMER))
                            .extract()
                            .cookie("JSESSIONID");
                } else if ("owner".equals(tagValue)) {
                    jSessionIdValue = login("test_owner", "test")
                            .and()
                            .cookie("JSESSIONID")
                            .header(HttpHeaders.LOCATION, "http://localhost:%s%s%s".formatted(serverPort, basePath, OWNER))
                            .extract()
                            .cookie("JSESSIONID");
                }
            }
        }
    }

    @AfterEach
    void afterEach() {
        logout()
                .and()
                .cookie("JSESSIONID", "");
        jSessionIdValue = null;
    }

}
