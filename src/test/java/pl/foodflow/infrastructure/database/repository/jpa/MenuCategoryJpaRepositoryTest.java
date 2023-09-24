package pl.foodflow.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.infrastructure.database.entity.MenuCategoryEntity;
import pl.foodflow.infrastructure.security.user.UserJpaRepository;
import pl.foodflow.integration.configuration.PersistenceContainerTestConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.foodflow.util.TestDataFactory.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(properties = "test.name=MenuCategoryJpaRepositoryTest")
class MenuCategoryJpaRepositoryTest {

    private MenuJpaRepository menuJpaRepository;
    private UserJpaRepository userJpaRepository;
    private OwnerJpaRepository ownerJpaRepository;
    private RestaurantJpaRepository restaurantJpaRepository;
    private MenuCategoryJpaRepository menuCategoryJpaRepository;

    @Test
    void shouldFindMenuCategoryByMenuId() {
        // given
        userJpaRepository.saveAndFlush(someUserEntity1());
        ownerJpaRepository.saveAndFlush(someOwnerEntity1());
        restaurantJpaRepository.saveAndFlush(someRestaurantEntity1());
        menuJpaRepository.saveAndFlush(someMenuEntity1());

        var menuCategories = List.of(someMenuCategoryEntity1(), someMenuCategoryEntity2(), someMenuCategoryEntity3());
        menuCategoryJpaRepository.saveAllAndFlush(menuCategories);

        // when
        List<MenuCategoryEntity> availableMenuCategories = menuCategoryJpaRepository
                .findAllByMenuMenuId(someMenuEntity1().getMenuId());
        // then
        assertThat(availableMenuCategories).hasSize(3);
    }
}