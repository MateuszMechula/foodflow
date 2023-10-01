package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.foodflow.infrastructure.security.user.UserService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.foodflow.api.controller.owner.OwnerHomePageController.OWNER;

@WebMvcTest(OwnerHomePageController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OwnerHomePageControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "testOwner", roles = "OWNER")
    void ownerHomePageWorksCorrectly() throws Exception {
        //given
        when(userService.getUsernameFromAuth()).thenReturn("testOwner");
        //when,then
        mockMvc.perform(get(OWNER))
                .andExpect(status().isOk())
                .andExpect(view().name("owner_home_page"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attribute("username", "testOwner"));
    }
}