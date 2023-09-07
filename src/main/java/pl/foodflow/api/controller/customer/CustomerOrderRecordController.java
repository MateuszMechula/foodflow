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
import pl.foodflow.business.OrderRecordService;
import pl.foodflow.business.RestaurantService;
import pl.foodflow.business.exceptions.OrderRecordNotFoundException;
import pl.foodflow.domain.Customer;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.infrastructure.security.user.UserService;

import java.util.List;

import static pl.foodflow.api.controller.customer.CustomerOrderRecordController.CUSTOMER;

@Controller
@AllArgsConstructor
@RequestMapping(value = CUSTOMER)
public class CustomerOrderRecordController {

    public static final String CUSTOMER = "/customer";
    public static final String CUSTOMER_ORDER = "/order";
    public static final String CUSTOMER_ORDERS = "/check-orders";

    private final OrderProcessingService orderProcessingService;
    private final OrderRecordService orderRecordService;
    private final RestaurantService restaurantService;
    private final CustomerService customerService;
    private final UserService userService;

    @GetMapping(value = CUSTOMER_ORDER + "/{restaurantId}")
    public String restaurantDetails(
            @PathVariable Long restaurantId,
            HttpSession session,
            Model model) {

        Restaurant restaurant = restaurantService.findById(restaurantId);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("orderDTO", new OrderDTO());

        SearchAddressDTO searchAddressDTO = getSessionSearchAddress(session);
        model.addAttribute("searchAddressDTO", searchAddressDTO);

        return "customer_order_form";
    }

    @GetMapping(value = CUSTOMER_ORDERS)
    public String checkCustomerOrders(
            Authentication auth,
            Model model) {

        return getOrders(auth, model);
    }

    @PostMapping(value = CUSTOMER_ORDERS)
    public String deleteOrderRecord(
            @RequestParam Long orderRecordId,
            Authentication auth,
            Model model) {
        try {
            boolean deletionAllowed = orderRecordService.deleteOrderWithPermission(orderRecordId);

            if (deletionAllowed) {
                model.addAttribute("successMessage", "Order successfully deleted");
            } else {
                model.addAttribute("errorMessage",
                        "Order cannot be deleted as it was placed more than 5 minutes ago");
            }
        } catch (OrderRecordNotFoundException e) {
            model.addAttribute("errorMessage", "Order not found");
        }
        return getOrders(auth, model);
    }

    @PostMapping(value = CUSTOMER_ORDER + "/{restaurantId}")
    public String submitOrder(
            @PathVariable Long restaurantId,
            @ModelAttribute("orderDTO") OrderDTO orderDTO,
            HttpSession session,
            Authentication auth,
            Model model
    ) {
        String username = auth.getName();
        long userId = getUserIdFromAuthentication(username);
        Customer customer = getCustomerByUserId(userId);

        SearchAddressDTO searchAddressDTO = getSessionSearchAddress(session);
        setDeliveryAddress(orderDTO, searchAddressDTO);

        OrderRecord savedOrderRecord = orderProcessingService
                .processAndCreateOrder(restaurantId, customer.withUserId((int) userId), orderDTO);

        model.addAttribute("orderRecord", savedOrderRecord);
        return "customer_order_information";
    }

    private long getUserIdFromAuthentication(String username) {
        return Long.parseLong(String.valueOf(userService.findByUsername(username).getUserId()));
    }

    private Customer getCustomerByUserId(long userId) {
        return customerService.findByUserId(userId);
    }

    private SearchAddressDTO getSessionSearchAddress(HttpSession session) {
        return (SearchAddressDTO) session.getAttribute("searchAddressDTO");
    }

    private void setDeliveryAddress(OrderDTO orderDTO, SearchAddressDTO searchAddressDTO) {
        String deliveryAddress = searchAddressDTO.getStreet() + ", " +
                searchAddressDTO.getPostalCode() + " " +
                searchAddressDTO.getCity();
        orderDTO.setDeliveryAddress(deliveryAddress);
    }

    private String getOrders(Authentication auth, Model model) {
        String username = auth.getName();
        long userId = getUserIdFromAuthentication(username);

        List<OrderRecord> allOrdersWithOrderStatusInProgress =
                orderRecordService.getAllCustomerOrdersWithOrderStatusInProgress(userId);
        List<OrderRecord> allOrdersWithOrderStatusCompleted =
                orderRecordService.getAllCustomerOrdersWithOrderStatusCompleted(userId);

        model.addAttribute("allOrdersWithOrderStatusInProgress", allOrdersWithOrderStatusInProgress);
        model.addAttribute("allOrdersWithOrderStatusCompleted", allOrdersWithOrderStatusCompleted);

        return "customer_orders_view";
    }
}
