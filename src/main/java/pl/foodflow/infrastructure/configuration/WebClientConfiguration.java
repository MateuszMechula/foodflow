package pl.foodflow.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import pl.foodflow.infrastructure.weather.ApiClient;
import pl.foodflow.infrastructure.weather.api.ApisApi;

@Configuration
public class WebClientConfiguration {
    public static final String API_KEY = "245a5a2e617b4ed099d165611232510";

    @Value("${api.weather.url}")
    private String weatherUrl;

    @Bean
    @Qualifier("weatherApiWebClient")
    public WebClient webClient(ObjectMapper objectMapper) {

        final var strategies = ExchangeStrategies
                .builder()
                .codecs(configurer -> {
                    configurer
                            .defaultCodecs()
                            .jackson2JsonEncoder(
                                    new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
                    configurer
                            .defaultCodecs()
                            .jackson2JsonDecoder(
                                    new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));
                }).build();
        return WebClient.builder()
                .exchangeStrategies(strategies)
                .build();
    }

    @Bean
    public ApiClient apiClient(final WebClient webClient) {
        ApiClient apiClient = new ApiClient(webClient);
        apiClient.setApiKey(API_KEY);
        apiClient.setBasePath(weatherUrl);
        return apiClient;
    }

    @Bean
    public ApisApi apisApi(final ApiClient apiClient) {
        return new ApisApi(apiClient);
    }
}
