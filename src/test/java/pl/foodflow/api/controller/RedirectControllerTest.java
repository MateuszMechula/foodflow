package pl.foodflow.api.controller;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RedirectController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class RedirectControllerTest {
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "CUSTOMER")
    public void testRedirectForCustomer() throws Exception {
        mockMvc.perform(get("/redirect-home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer"));
    }

    @Test
    @WithMockUser(authorities = "OWNER")
    public void testRedirectForOwner() throws Exception {
        mockMvc.perform(get("/redirect-home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/owner"));
    }

    @Test
    @WithMockUser(authorities = "OTHER_ROLE")
    public void testRedirectForOtherRole() throws Exception {
        mockMvc.perform(get("/redirect-home"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
