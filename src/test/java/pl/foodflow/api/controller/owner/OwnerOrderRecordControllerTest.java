package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.foodflow.business.OrderRecordService;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.enums.OrderStatus;
import pl.foodflow.infrastructure.security.user.User;
import pl.foodflow.infrastructure.security.user.UserService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.foodflow.api.controller.owner.OwnerOrderRecordController.OWNER;
import static pl.foodflow.api.controller.owner.OwnerOrderRecordController.OWNER_ORDERS;
import static pl.foodflow.util.TestDataFactory.someOrderRecord3;

@WebMvcTest(OwnerOrderRecordController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OwnerOrderRecordControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private OrderRecordService orderRecordService;
    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "testOwner", roles = "OWNER")
    void checkOwnerOrdersWorksCorrectly() throws Exception {
        //given
        Integer userId = 1;
        List<OrderRecord> orderRecordsInProgress = List.of(someOrderRecord3());
        List<OrderRecord> orderRecordsCompleted = List.of(someOrderRecord3());

        when(userService.getUserIdByAuth()).thenReturn(userId);
        when(orderRecordService.getAllOwnerOrdersWithStatus(userId, OrderStatus.IN_PROGRESS))
                .thenReturn(orderRecordsInProgress);
        when(orderRecordService.getAllOwnerOrdersWithStatus(userId, OrderStatus.COMPLETED))
                .thenReturn(orderRecordsCompleted);
        //when,then
        mockMvc.perform(get(OWNER + OWNER_ORDERS)
                        .session(new MockHttpSession()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allOwnerOrdersWithStatusInProgress"))
                .andExpect(model().attribute("allOwnerOrdersWithStatusInProgress", orderRecordsInProgress))
                .andExpect(model().attributeExists("allOwnerOrdersWithOrderStatusCompleted"))
                .andExpect(model().attribute("allOwnerOrdersWithOrderStatusCompleted", orderRecordsCompleted))
                .andExpect(view().name("owner_orders_view"));
    }

    @Test
    @WithMockUser(username = "testOwner", roles = "OWNER")
    void completedOrderWorksCorrectly() throws Exception {
        //given
        Long orderRecordId = 1L;
        User user = User.builder().userName("testOwner").build();

        when(userService.findByUsername(user.getUserName())).thenReturn(user);
        doNothing().when(orderRecordService).changeOrderStatusToCompleted(orderRecordId);
        //when,then
        mockMvc.perform(post(OWNER + OWNER_ORDERS)
                        .param("orderRecordId", String.valueOf(orderRecordId)))
                .andExpect(status().isOk())
                .andExpect(view().name("owner_orders_view"));
        verify(orderRecordService).changeOrderStatusToCompleted(orderRecordId);
    }
}