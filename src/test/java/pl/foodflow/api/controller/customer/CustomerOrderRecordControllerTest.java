package pl.foodflow.api.controller.customer;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.foodflow.api.dto.OrderDTO;
import pl.foodflow.business.CustomerService;
import pl.foodflow.business.OrderProcessingService;
import pl.foodflow.business.OrderRecordService;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.business.exceptions.OrderRecordNotFoundException;
import pl.foodflow.domain.Customer;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.enums.OrderStatus;
import pl.foodflow.infrastructure.security.user.User;
import pl.foodflow.infrastructure.security.user.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.foodflow.api.controller.customer.CustomerOrderRecordController.*;
import static pl.foodflow.util.TestDataFactory.*;

@WebMvcTest(CustomerOrderRecordController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CustomerOrderRecordControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private OrderRecordService orderRecordService;

    @MockBean
    private OrderProcessingService orderProcessingService;

    @Test
    @WithMockUser(username = "testCustomer", roles = "CUSTOMER")
    void checkCustomerOrdersWorksCorrectly() throws Exception {
        //given
        Integer userId = 1;
        List<OrderRecord> orderRecordsInProgress = List.of(someOrderRecord3());
        List<OrderRecord> orderRecordsCompleted = List.of(someOrderRecord3());

        when(userService.getUserIdByAuth()).thenReturn(userId);
        when(orderRecordService.getAllCustomerOrdersWithStatus(userId, OrderStatus.IN_PROGRESS))
                .thenReturn(orderRecordsInProgress);
        when(orderRecordService.getAllCustomerOrdersWithStatus(userId, OrderStatus.COMPLETED))
                .thenReturn(orderRecordsCompleted);

        //when,then
        mockMvc.perform(get(CUSTOMER + CUSTOMER_ORDERS)
                        .session(new MockHttpSession()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allOrdersWithOrderStatusInProgress"))
                .andExpect(model().attribute("allOrdersWithOrderStatusInProgress", orderRecordsInProgress))
                .andExpect(model().attributeExists("allOrdersWithOrderStatusCompleted"))
                .andExpect(model().attribute("allOrdersWithOrderStatusCompleted", orderRecordsCompleted))
                .andExpect(view().name("customer_orders_view"));

    }

    @Test
    void restaurantDetailsWorksCorrectly() throws Exception {
        //given
        Long restaurantId = 1L;
        Restaurant restaurant = someRestaurant1();
        when(restaurantService.getRestaurantById(restaurantId)).thenReturn(restaurant);

        // When
        MockHttpSession session = new MockHttpSession();

        // Perform the request and verify the results
        mockMvc.perform(get(CUSTOMER + CUSTOMER_ORDER + "/{restaurantId}", restaurantId)
                        .session(session))

                // Then
                .andExpect(status().isOk())
                .andExpect(model().attribute("restaurant", restaurant))
                .andExpect(model().attribute("orderDTO", new OrderDTO()))
                .andExpect(view().name("customer_order_form"));
    }

    @Test
    @WithMockUser(username = "testCustomer", roles = "CUSTOMER")
    void deleteOrderRecordWorksCorrectly() throws Exception {
        //given
        Long orderRecordId = 1L;
        User user = User.builder().userName("testCustomer").build();

        when(orderRecordService.deleteOrderWithPermission(orderRecordId)).thenReturn(true);
        when(userService.findByUsername(any())).thenReturn(user);
        //when,then
        mockMvc.perform(post(CUSTOMER + CUSTOMER_ORDERS)
                        .param("orderRecordId", orderRecordId.toString())
                        .session(new MockHttpSession()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("successMessage"))
                .andExpect(model().attribute("successMessage", "Order successfully deleted"))
                .andExpect(view().name("customer_orders_view"));
    }

    @Test
    @WithMockUser(username = "testCustomer", roles = "CUSTOMER")
    void deleteOrderRecordErrorCase() throws Exception {
        // given
        Long orderRecordId = 1L;
        User user = User.builder().userName("testCustomer").build();

        when(orderRecordService.deleteOrderWithPermission(orderRecordId)).thenReturn(false);
        when(userService.findByUsername(any())).thenReturn(user);
        // When
        mockMvc.perform(post(CUSTOMER + CUSTOMER_ORDERS)
                        .param("orderRecordId", orderRecordId.toString())
                        .session(new MockHttpSession()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "Order cannot be deleted as it was placed more than 5 minutes ago"))
                .andExpect(view().name("customer_orders_view"));
    }

    @Test
    @WithMockUser(username = "testCustomer", roles = "CUSTOMER")
    void deleteOrderRecordOrderNotFound() throws Exception {
        //given
        Long orderRecordId = 1L;
        User user = User.builder().userName("testCustomer").build();


        when(userService.findByUsername(any())).thenReturn(user);
        when(orderRecordService.deleteOrderWithPermission(orderRecordId)).thenThrow(OrderRecordNotFoundException.class);
        //when,then
        mockMvc.perform(post(CUSTOMER + CUSTOMER_ORDERS)
                        .param("orderRecordId", orderRecordId.toString())
                        .session(new MockHttpSession()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "Order not found"));
    }

    @Test
    @WithMockUser(username = "testCustomer", roles = "CUSTOMER")
    void submitOrderWorksCorrectly() throws Exception {
        //given
        Long restaurantId = 1L;
        Integer userId = 1;
        OrderDTO orderDTO = someOrderDTO();
        Customer customer = someCustomer1();
        OrderRecord orderRecord = someOrderRecord1();

        when(userService.getUserIdByAuth()).thenReturn(userId);
        when(customerService.getCustomerByUserId(userId)).thenReturn(customer);
        when(orderProcessingService.processAndCreateOrder(restaurantId, customer, orderDTO)).thenReturn(orderRecord);

        //when,then
        mockMvc.perform(post(CUSTOMER + CUSTOMER_ORDER + RESTAURANT_ID, restaurantId)
                        .sessionAttr("searchAddressDTO", someSearchAddressDTO())
                        .session(new MockHttpSession())
                        .flashAttr("orderDTO", orderDTO))
                .andExpect(status().isOk())
                .andExpect(view().name("customer_order_information"))
                .andExpect(model().attributeExists("orderRecord"))
                .andExpect(model().attribute("orderRecord", orderRecord))
                .andExpect(model().hasNoErrors());
    }
}