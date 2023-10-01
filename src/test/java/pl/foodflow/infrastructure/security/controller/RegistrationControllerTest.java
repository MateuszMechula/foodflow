package pl.foodflow.infrastructure.security.controller;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.foodflow.infrastructure.security.user.UserDTO;
import pl.foodflow.infrastructure.security.user.UserService;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.foodflow.infrastructure.security.controller.RegistrationController.*;
import static pl.foodflow.util.TestDataFactory.someUserDTO1;

@WebMvcTest(RegistrationController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class RegistrationControllerTest {
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    void showRegistrationFormWorksCorrectly() throws Exception {
        mockMvc.perform(get(REGISTRATION + REGISTRATION_FORM))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userDTO"))
                .andExpect(view().name("user_registration"));
    }

    @Test
    void registerUserWorksCorrectly() throws Exception {
        //given
        UserDTO userDTO = someUserDTO1();
        //when,then
        mockMvc.perform(post(REGISTRATION + REGISTER)
                        .flashAttr("user", userDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/foodflow/registration/registration-form?success"));
        verify(userService).registerUser(userDTO);
    }
}