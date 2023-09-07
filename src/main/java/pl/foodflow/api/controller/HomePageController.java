package pl.foodflow.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    @GetMapping
    public String homePage() {
        return "redirect:/login";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "user_login";
    }
}
