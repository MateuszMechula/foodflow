package pl.foodflow.integration.support;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public interface WiremockTestSupport {

    default void stubForWeather(WireMockServer wireMockServer) {
        wireMockServer.stubFor(
                get(urlEqualTo("/current.json?q=warszawa&lang=pl&key=245a5a2e617b4ed099d165611232510"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBodyFile("wiremock/weather.json")));
    }
}
