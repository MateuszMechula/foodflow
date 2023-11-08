package pl.foodflow.api.controller.customer;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.foodflow.api.dto.WeatherDataDTO;
import pl.foodflow.infrastructure.weather.WeatherClientImpl;
import pl.foodflow.util.TestDataFactory;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.foodflow.api.controller.customer.CustomerWeatherController.*;

@WebMvcTest(CustomerWeatherController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CustomerWeatherControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private WeatherClientImpl weatherClient;

    @Test
    void shouldShowWeatherForm() throws Exception {
        mockMvc.perform(get(CUSTOMER + CHECK_WEATHER))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("weatherData"))
                .andExpect(view().name("customer_check_weather"))
                .andExpect(model().attribute("weatherData", new WeatherDataDTO()));
    }

    @Test
    void getWeatherByLocationParameterAndLang() throws Exception {
        //given
        String locationParameter = "Warszawa";
        String lang = "pl";
        WeatherDataDTO weatherDataDTO = TestDataFactory.weatherDataDTO();

        Mockito.when(weatherClient.getWeatherLocationParameter(locationParameter, lang))
                .thenReturn(Optional.of(weatherDataDTO));
        //when
        mockMvc.perform(get(CUSTOMER + CHECK_WEATHER_FORM)
                        .queryParam("locationParameter", locationParameter)
                        .queryParam("lang", lang))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("weatherData"))
                .andExpect(view().name("customer_check_weather"));
    }
}