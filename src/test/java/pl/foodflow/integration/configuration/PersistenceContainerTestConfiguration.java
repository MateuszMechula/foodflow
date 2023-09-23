package pl.foodflow.integration.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@TestConfiguration
public class PersistenceContainerTestConfiguration {

    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String POSTGRESQL = "postgresql";
    public static final String POSTGRESQL_CONTAINER = "postgres:15.0";
    private final Map<String, PostgreSQLContainer<?>> containers = new HashMap<>();

    @Bean
    @Qualifier(POSTGRESQL)
    PostgreSQLContainer<?> postgreSQLContainer(@Value("${test.name}") String testName) {
        if (!containers.containsKey(testName)) {
            PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(POSTGRESQL_CONTAINER)
                    .withUsername(USERNAME)
                    .withPassword(PASSWORD);
            postgreSQLContainer.start();
            containers.put(testName, postgreSQLContainer);
        }

        return containers.get(testName);
    }

    @Bean
    DataSource dataSource(@Qualifier(POSTGRESQL) final PostgreSQLContainer<?> container) {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .driverClassName(container.getDriverClassName())
                .url(container.getJdbcUrl())
                .username(container.getUsername())
                .password(container.getPassword())
                .build();
    }
}
