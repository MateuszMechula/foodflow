package pl.foodflow.api.controller.rest.owner;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.foodflow.business.OrderRecordService;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.enums.OrderStatus;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = OwnerOrderRecordRestController.ORDER_RECORDS)
public class OwnerOrderRecordRestController {

    public static final String ORDER_RECORDS = "/owner/api/v1/order-records";
    public static final String COMPLETE_ORDER = "/complete/{orderRecordId}";

    private final OrderRecordService orderRecordService;

    @GetMapping
    public ResponseEntity<List<OrderRecord>> getAllOwnerOrdersWithStatus(
            @RequestParam Integer userId,
            @RequestParam OrderStatus status
    ) {
        List<OrderRecord> orders = orderRecordService.getAllOwnerOrdersWithStatus(userId, status);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @PostMapping(value = COMPLETE_ORDER)
    public ResponseEntity<Void> completeOrder(
            @PathVariable Long orderRecordId
    ) {
        log.info("Marking order as completed: {}", orderRecordId);
        orderRecordService.changeOrderStatusToCompleted(orderRecordId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
