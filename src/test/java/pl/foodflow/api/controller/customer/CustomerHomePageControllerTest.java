package pl.foodflow.api.controller.customer;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CustomerHomePageController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CustomerHomePageControllerTest {
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testCustomer", roles = "CUSTOMER")
    public void customerHomePageWorksCorrectly() throws Exception {
        mockMvc.perform(get(CustomerHomePageController.CUSTOMER))
                .andExpect(status().isOk())
                .andExpect(view().name("customer_home_page"));
    }
}