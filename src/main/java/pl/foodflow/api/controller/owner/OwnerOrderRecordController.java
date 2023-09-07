package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.foodflow.business.OrderRecordService;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.infrastructure.security.user.UserService;

import java.util.List;

import static pl.foodflow.api.controller.owner.OwnerOrderRecordController.OWNER;

@Controller
@AllArgsConstructor
@RequestMapping(value = OWNER)
public class OwnerOrderRecordController {

    public static final String OWNER = "/owner";
    public static final String OWNER_ORDERS = "/check-orders";
    private final OrderRecordService orderRecordService;
    private final UserService userService;

    @GetMapping(value = OWNER_ORDERS)
    public String checkOwnerOrders(
            Authentication auth,
            Model model
    ) {
        return getOrders(auth, model);
    }

    @PostMapping(value = OWNER_ORDERS)
    public String completedOrder(
            @RequestParam Long orderRecordId,
            Authentication auth,
            Model model
    ) {
        orderRecordService.changeOrderStatusToCompleted(orderRecordId);
        return getOrders(auth, model);
    }

    private String getOrders(Authentication auth, Model model) {
        String username = auth.getName();
        long userId = getUserIdFromAuthentication(username);

        List<OrderRecord> allOwnerOrdersWithStatusInProgress =
                orderRecordService.getAllOwnerOrdersWithOrderStatusInProgress(userId);
        List<OrderRecord> allOwnerOrdersWithOrderStatusCompleted =
                orderRecordService.getAllOwnerOrdersWithOrderStatusCompleted(userId);

        model.addAttribute("allOwnerOrdersWithStatusInProgress", allOwnerOrdersWithStatusInProgress);
        model.addAttribute("allOwnerOrdersWithOrderStatusCompleted", allOwnerOrdersWithOrderStatusCompleted);

        return "owner_orders_view";
    }

    private long getUserIdFromAuthentication(String username) {
        return Long.parseLong(String.valueOf(userService.findByUsername(username).getUserId()));
    }
}
