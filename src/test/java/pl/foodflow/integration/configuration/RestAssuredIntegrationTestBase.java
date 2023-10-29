package pl.foodflow.integration.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import pl.foodflow.integration.support.AuthenticationTestSupport;
import pl.foodflow.integration.support.ControllerTestSupport;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public abstract class RestAssuredIntegrationTestBase
        extends AbstractIntegrationTest
        implements ControllerTestSupport, AuthenticationTestSupport {

    protected static WireMockServer wireMockServer;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(
                wireMockConfig()
                        .port(9090)
                        .extensions(new ResponseTemplateTransformer(false))

        );
        wireMockServer.start();
    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    @AfterEach
    void afterEach() {
        wireMockServer.resetAll();
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
                .contentType(ContentType.JSON);
    }

    public RequestSpecification requestSpecificationNoAuthentication() {
        return restAssuredBase();
    }
}
