package pl.foodflow.api.controller.customer;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.foodflow.api.dto.OrderDTO;
import pl.foodflow.api.dto.SearchAddressDTO;
import pl.foodflow.business.CustomerService;
import pl.foodflow.business.OrderProcessingService;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.domain.Customer;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.infrastructure.security.UserService;

import static pl.foodflow.api.controller.customer.CustomerOrderController.CUSTOMER;

@Controller
@AllArgsConstructor
@RequestMapping(value = CUSTOMER)
public class CustomerOrderController {

    public static final String CUSTOMER = "/customer";

    public static final String CUSTOMER_ORDER = "/order";

    private final RestaurantService restaurantService;
    private final UserService userService;
    private final OrderProcessingService orderProcessingService;
    private final CustomerService customerService;

    @GetMapping(value = CUSTOMER_ORDER + "/{restaurantId}")
    public String restaurantDetails(
            @PathVariable Long restaurantId,
            HttpSession session,
            Model model) {

        Restaurant restaurant = restaurantService.findById(restaurantId);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("orderDTO", new OrderDTO());

        SearchAddressDTO searchAddressDTO = (SearchAddressDTO) session.getAttribute("searchAddressDTO");

        model.addAttribute("searchAddressDTO", searchAddressDTO);
        return "customer_order_form";
    }

    @PostMapping(value = CUSTOMER_ORDER + "/{restaurantId}")
    public String submitOrder(
            @PathVariable Long restaurantId,
            @ModelAttribute("orderDTO") OrderDTO orderDTO,
            HttpSession session,
            Authentication authentication
    ) {
        String username = authentication.getName();
        long userId = Long.parseLong(String.valueOf(userService.findByUserName(username).getUserId()));
        Customer customer = customerService.findByUserId(userId);

        SearchAddressDTO searchAddressDTO = (SearchAddressDTO) session.getAttribute("searchAddressDTO");
        String deliveryAddress = searchAddressDTO.getStreet() + ", " + searchAddressDTO.getPostalCode() + " " + searchAddressDTO.getCity();
        orderDTO.setDeliveryAddress(deliveryAddress);

        orderProcessingService.processAndCreateOrder(restaurantId, customer.withUserId((int) userId), orderDTO);

        return "order_information";
    }
}
