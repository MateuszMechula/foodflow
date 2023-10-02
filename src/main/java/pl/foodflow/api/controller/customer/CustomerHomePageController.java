package pl.foodflow.api.controller.customer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.foodflow.infrastructure.security.user.UserService;

@Slf4j
@Controller
@AllArgsConstructor
public class CustomerHomePageController {

    public static final String CUSTOMER = "/customer";
    private final UserService userService;

    @GetMapping(value = CUSTOMER)
    public String customerHomePage() {
        String username = userService.getUsernameFromAuth();
        log.info("Customer home page accessed by user: {}", username);

        return "customer_home_page";
    }

}
