package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class OwnerController {

    public static final String OWNER = "/owner";

    @GetMapping(value = OWNER)
    public String ownerPage() {
        return "owner_home_page";
    }

}
