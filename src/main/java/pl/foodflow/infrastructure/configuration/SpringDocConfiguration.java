package pl.foodflow.infrastructure.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.foodflow.FoodFlowApplication;

import java.util.List;

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
                        .description("API foodflow")
                        .version("1.0"))
                .tags(List.of(
                        new Tag().name("customer orderRecord"),
                        new Tag().name("customer search restaurants"),
                        new Tag().name("owner categoryItem"),
                        new Tag().name("owner menuCategory"),
                        new Tag().name("owner menu"),
                        new Tag().name("owner orderRecord"),
                        new Tag().name("owner restaurantAddress"),
                        new Tag().name("owner restaurant"),
                        new Tag().name("user")
                ));
    }

    private Contact contact() {
        return new Contact()
                .name("Foodflow")
                .url("https://github.com/MateuszMechula/foodflow")
                .email("mateuszmechua@gmail.com");
    }
}
