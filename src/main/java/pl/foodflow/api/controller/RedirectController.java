package pl.foodflow.api.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/redirect-home")
    public String redirectHome() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("CUSTOMER"))) {
            return "redirect:/customer";
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("OWNER"))) {
            return "redirect:/owner";
        } else {
            return "redirect:/";
        }
    }
}
