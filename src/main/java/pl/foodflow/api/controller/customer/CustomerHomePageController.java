package pl.foodflow.api.controller.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class CustomerHomePageController {

    public static final String CUSTOMER = "/customer";

    @GetMapping(value = CUSTOMER)
    public String ownerPage() {
        return "customer_home_page";
    }

}
