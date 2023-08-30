package pl.foodflow.api.controller;

import org.springframework.stereotype.Controller;

@Controller
public class HomePageController {

    public String homePage() {
        return "redirect:/login";
    }
}
