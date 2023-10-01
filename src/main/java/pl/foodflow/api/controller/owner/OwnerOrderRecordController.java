package pl.foodflow.api.controller.owner;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.foodflow.business.OrderRecordService;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.enums.OrderStatus;
import pl.foodflow.infrastructure.security.user.UserService;

import java.util.List;

import static pl.foodflow.api.controller.owner.OwnerOrderRecordController.OWNER;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping(value = OWNER)
public class OwnerOrderRecordController {

    public static final String OWNER = "/owner";
    public static final String OWNER_ORDERS = "/check-orders";
    private final OrderRecordService orderRecordService;
    private final UserService userService;

    @GetMapping(value = OWNER_ORDERS)
    public String checkOwnerOrders(Model model) {
        log.info("Fetching owner orders for user");
        return getOrders(model);
    }

    @PostMapping(value = OWNER_ORDERS)
    public String completedOrder(@RequestParam Long orderRecordId, Model model) {
        log.info("Marking order as completed: {}", orderRecordId);
        orderRecordService.changeOrderStatusToCompleted(orderRecordId);
        return getOrders(model);
    }

    private String getOrders(Model model) {
        Integer userId = userService.getUserIdByAuth();

        List<OrderRecord> allOwnerOrdersWithStatusInProgress =
                orderRecordService.getAllOwnerOrdersWithStatus(userId, OrderStatus.IN_PROGRESS);
        List<OrderRecord> allOwnerOrdersWithOrderStatusCompleted =
                orderRecordService.getAllOwnerOrdersWithStatus(userId, OrderStatus.COMPLETED);

        model.addAttribute("allOwnerOrdersWithStatusInProgress", allOwnerOrdersWithStatusInProgress);
        model.addAttribute("allOwnerOrdersWithOrderStatusCompleted", allOwnerOrdersWithOrderStatusCompleted);

        return "owner_orders_view";
    }
}
