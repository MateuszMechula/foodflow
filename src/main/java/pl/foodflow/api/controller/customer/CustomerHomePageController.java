package pl.foodflow.api.controller.customer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@AllArgsConstructor
public class CustomerHomePageController {

    public static final String CUSTOMER = "/customer";

    @GetMapping(value = CUSTOMER)
    public String customerHomePage(Authentication authentication) {
        String username = authentication.getName();
        log.info("Customer home page accessed by user: {}", username);

        return "customer_home_page";
    }

}
