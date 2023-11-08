package pl.foodflow.api.controller.customer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.foodflow.api.dto.WeatherDataDTO;
import pl.foodflow.infrastructure.weather.WeatherClientImpl;

import java.util.Optional;

import static pl.foodflow.api.controller.customer.CustomerWeatherController.CUSTOMER;

@Controller
@Slf4j
@RequestMapping(value = CUSTOMER)
@AllArgsConstructor
public class CustomerWeatherController {

    public static final String CUSTOMER = "/customer";
    public static final String CHECK_WEATHER = "/check-weather";
    public static final String CHECK_WEATHER_FORM = "/check-weather-form";
    private final WeatherClientImpl weatherClient;

    @GetMapping(value = CHECK_WEATHER)
    public String showWeatherForm(Model model) {
        log.info("Checking weather.");
        model.addAttribute("weatherData", new WeatherDataDTO());
        return "customer_check_weather";
    }

    @GetMapping(value = CHECK_WEATHER_FORM)
    public String getWeatherByLocationParameterAndLang(
            @RequestParam("locationParameter") String locationParameter,
            @RequestParam("lang") String lang,
            Model model
    ) {
        Optional<WeatherDataDTO> optionalWeatherData = weatherClient
                .getWeatherLocationParameter(locationParameter, lang);

        optionalWeatherData.ifPresent(weatherDataDTO ->
                model.addAttribute("weatherData", weatherDataDTO));
        return "customer_check_weather";
    }
}
