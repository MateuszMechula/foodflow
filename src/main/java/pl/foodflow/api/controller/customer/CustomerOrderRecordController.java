package pl.foodflow.api.controller.customer;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import pl.foodflow.enums.OrderStatus;
import pl.foodflow.infrastructure.security.user.UserService;

import java.util.List;

import static pl.foodflow.api.controller.customer.CustomerOrderRecordController.CUSTOMER;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping(value = CUSTOMER)
public class CustomerOrderRecordController {

    public static final String CUSTOMER = "/customer";
    public static final String CUSTOMER_ORDER = "/order";
    public static final String CUSTOMER_ORDERS = "/check-orders";

    private final UserService userService;
    private final CustomerService customerService;
    private final RestaurantService restaurantService;
    private final OrderRecordService orderRecordService;
    private final OrderProcessingService orderProcessingService;

    @GetMapping(value = CUSTOMER_ORDERS)
    public String checkCustomerOrders(
            Authentication auth,
            Model model) {
        log.info("Received request to check customer orders.");
        return getOrders(auth, model);
    }

    @GetMapping(value = CUSTOMER_ORDER + "/{restaurantId}")
    public String restaurantDetails(
            @PathVariable Long restaurantId,
            HttpSession session,
            Model model) {
        log.info("Received request for restaurant details with ID: {}", restaurantId);

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("orderDTO", new OrderDTO());

        SearchAddressDTO searchAddressDTO = getSessionSearchAddress(session);
        model.addAttribute("searchAddressDTO", searchAddressDTO);

        return "customer_order_form";
    }

    @PostMapping(value = CUSTOMER_ORDERS)
    public String deleteOrderRecord(
            @RequestParam Long orderRecordId,
            Authentication auth,
            Model model) {
        log.info("Received request to delete order record with ID: {}", orderRecordId);
        try {
            boolean deletionAllowed = orderRecordService.deleteOrderWithPermission(orderRecordId);

            if (deletionAllowed) {
                model.addAttribute("successMessage", "Order successfully deleted");
                log.info("Order with ID {} successfully deleted.", orderRecordId);
            } else {
                model.addAttribute("errorMessage",
                        "Order cannot be deleted as it was placed more than 5 minutes ago");
                log.warn("Order with ID {} cannot be deleted due to time constraint.", orderRecordId);
            }
        } catch (OrderRecordNotFoundException e) {
            model.addAttribute("errorMessage", "Order not found");
            log.error("Order with ID {} not found.", orderRecordId);
        }
        return getOrders(auth, model);
    }

    @PostMapping(value = CUSTOMER_ORDER + "/{restaurantId}")
    public String submitOrder(
            @PathVariable Long restaurantId,
            @Valid @ModelAttribute("orderDTO") OrderDTO orderDTO,
            HttpSession session,
            Authentication auth,
            Model model
    ) {
        log.info("Received request to submit an order for restaurant with ID: {}", restaurantId);
        String username = auth.getName();
        long userId = getUserIdFromAuthentication(username);
        Customer customer = getCustomerByUserId(userId);

        SearchAddressDTO searchAddressDTO = getSessionSearchAddress(session);
        setDeliveryAddress(orderDTO, searchAddressDTO);

        OrderRecord savedOrderRecord = orderProcessingService
                .processAndCreateOrder(restaurantId, customer.withUserId((int) userId), orderDTO);

        model.addAttribute("orderRecord", savedOrderRecord);
        log.info("Order successfully submitted.");
        return "customer_order_information";
    }

    private long getUserIdFromAuthentication(String username) {
        return Long.parseLong(String.valueOf(userService.findByUsername(username).getUserId()));
    }

    private Customer getCustomerByUserId(long userId) {
        return customerService.getCustomerByUserId(userId);
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
                orderRecordService.getAllCustomerOrdersWithStatus(userId, OrderStatus.IN_PROGRESS);
        List<OrderRecord> allOrdersWithOrderStatusCompleted =
                orderRecordService.getAllCustomerOrdersWithStatus(userId, OrderStatus.COMPLETED);

        model.addAttribute("allOrdersWithOrderStatusInProgress", allOrdersWithOrderStatusInProgress);
        model.addAttribute("allOrdersWithOrderStatusCompleted", allOrdersWithOrderStatusCompleted);

        return "customer_orders_view";
    }
}
