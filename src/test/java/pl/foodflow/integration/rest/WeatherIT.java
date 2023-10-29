package pl.foodflow.integration.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.api.dto.WeatherDataDTO;
import pl.foodflow.business.dao.WeatherDAO;
import pl.foodflow.integration.configuration.RestAssuredIntegrationTestBase;
import pl.foodflow.integration.support.WiremockTestSupport;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@TestPropertySource(properties = "test.name=WeatherIT")
public class WeatherIT extends RestAssuredIntegrationTestBase
        implements WiremockTestSupport {

    @Autowired
    private WeatherDAO weatherClient;

    @Test
    public void getWeatherByLocationParameterAndLand() {
        //given
        stubForWeather(wireMockServer);

        //when
        Optional<WeatherDataDTO> weatherData = weatherClient.getWeatherLocationParameter("warszawa", "pl");
        assertThat(weatherData).isPresent();
        WeatherDataDTO weatherDataDTO = weatherData.get();

        //then
        assertEquals("Warszawa", weatherDataDTO.getName());
        assertEquals("Bezchmurnie", weatherDataDTO.getText());
    }

}
