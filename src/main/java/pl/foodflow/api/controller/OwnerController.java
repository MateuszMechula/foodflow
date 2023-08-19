package pl.foodflow.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.foodflow.api.dto.OwnerDTO;
import pl.foodflow.api.dto.mapper.OwnerMapper;
import pl.foodflow.business.OwnerService;
import pl.foodflow.domain.Owner;

@Controller
@AllArgsConstructor
public class OwnerController {

    public static final String OWNER = "/owner";

    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;

    @GetMapping(value = OWNER)
    public ModelAndView ownerPage(Model model) {
        model.addAttribute("ownerDTO", new OwnerDTO());
        return new ModelAndView("owner");
    }


    @PostMapping(value = OWNER)
    public String addOwner(
            @ModelAttribute("ownerDTO") OwnerDTO ownerDTO,
            ModelMap model) {

        Owner owner = ownerMapper.map(ownerDTO);
        ownerService.saveOwner(owner);
        model.addAttribute("ownerDTO", ownerDTO);
        return "redirect:/owner";
    }
}
