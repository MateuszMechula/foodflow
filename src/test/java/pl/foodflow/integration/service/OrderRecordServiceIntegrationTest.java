package pl.foodflow.integration.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.business.*;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.domain.Owner;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.infrastructure.security.user.UserDTO;
import pl.foodflow.infrastructure.security.user.UserService;
import pl.foodflow.integration.configuration.PersistenceContainerTestConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static pl.foodflow.util.TestDataFactory.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(properties = "test.name=OrderRecordServiceIntegrationTest")
public class OrderRecordServiceIntegrationTest {

    private UserService userService;
    private MenuService menuService;
    private OwnerService ownerService;
    private CustomerService customerService;
    private RestaurantService restaurantService;
    private OrderRecordService orderRecordService;
    private MenuCategoryService menuCategoryService;
    private CategoryItemService categoryItemService;

    @Test
    @Transactional
    void shouldSaveAndRetrieveOrderRecord() {
        //given
        UserDTO userDTO = someUserDTO2();
        userService.registerUser(userDTO);
        customerService.saveCustomer(someCustomerIntegration());
        Owner owner = someOwnerIntegration();
        ownerService.saveOwner(someOwnerIntegration());
        Restaurant restaurant = someRestaurantIntegration().withOwner(owner);
        restaurantService.addRestaurant(restaurant);
        menuService.saveMenu(someMenuIntegration());
        menuCategoryService.saveMenuCategory(someMenuCategoryIntegration());
        categoryItemService.saveCategoryItem(someCategoryItemIntegration());


        OrderRecord orderRecord = someOrderIntegration();
        //when
        orderRecordService.saveOrderRecord(orderRecord);
        OrderRecord retrievedOrderRecord = orderRecordService.getOrderRecordById(orderRecord.getOrderRecordId());
        //then
        assertNotNull(retrievedOrderRecord);
        assertEquals(orderRecord.getOrderRecordId(), retrievedOrderRecord.getOrderRecordId());
    }
}
