package pl.foodflow.infrastructure.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.foodflow.FoodFlowApplication;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**")
                .packagesToScan(FoodFlowApplication.class.getPackageName())
                .build();
    }

    @Bean
    public OpenAPI springDocOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Foodflow")
                        .contact(contact())
                        .version("1.0"));
    }

    private Contact contact() {
        return new Contact()
                .name("Foodflow")
                .url("https://github.com/MateuszMechula/foodflow")
                .email("mateuszmechua@gmail.com");
    }
}
