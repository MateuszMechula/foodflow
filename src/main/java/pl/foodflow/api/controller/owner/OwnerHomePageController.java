package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@AllArgsConstructor
public class OwnerHomePageController {

    public static final String OWNER = "/owner";

    @GetMapping(value = OWNER)
    public String ownerHomePage(Model model, Authentication authentication) {

        String username = authentication.getName();
        model.addAttribute("username", username);

        log.info("Owner home page accessed by user: {}", username);

        return "owner_home_page";
    }

}
