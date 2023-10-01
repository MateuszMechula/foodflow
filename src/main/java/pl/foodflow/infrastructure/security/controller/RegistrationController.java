package pl.foodflow.infrastructure.security.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.foodflow.infrastructure.security.user.UserDTO;
import pl.foodflow.infrastructure.security.user.UserService;

import static pl.foodflow.infrastructure.security.controller.RegistrationController.REGISTRATION;

@Controller
@AllArgsConstructor
@RequestMapping(value = REGISTRATION)
public class RegistrationController {
    public static final String REGISTER = "/register";
    public static final String REGISTRATION = "/registration";
    public static final String REGISTRATION_FORM = "/registration-form";


    private final UserService userService;

    @GetMapping(value = REGISTRATION_FORM)
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "user_registration";
    }

    @PostMapping(value = REGISTER)
    public String registerUser(@ModelAttribute("user") UserDTO userDTO) {
        userService.registerUser(userDTO);
        return "redirect:/foodflow/registration/registration-form?success";
    }
}
