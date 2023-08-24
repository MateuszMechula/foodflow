package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class OwnerHomeController {

    public static final String OWNER = "/owner";

    @GetMapping(value = OWNER)
    public String ownerPage(
            Model model,
            Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("username", username);

        return "owner_home_page";
    }

}
