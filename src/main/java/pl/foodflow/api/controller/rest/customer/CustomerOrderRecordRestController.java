package pl.foodflow.api.controller.rest.customer;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.foodflow.api.dto.OrderDTO;
import pl.foodflow.business.CustomerService;
import pl.foodflow.business.OrderProcessingService;
import pl.foodflow.business.OrderRecordService;
import pl.foodflow.domain.Customer;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.enums.OrderStatus;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = CustomerOrderRecordRestController.ORDER_RECORDS)
public class CustomerOrderRecordRestController {
    public static final String ORDER_RECORDS = "/api/v1/customer/order-records";
    public static final String RESTAURANT_ID = "/{restaurantId}";
    public static final String CUSTOMER_ID = "/{customerId}";
    public static final String ORDER_RECORD_ID = "/{orderRecordId}";

    private final CustomerService customerService;
    private final OrderRecordService orderRecordService;
    private final OrderProcessingService orderProcessingService;

    @GetMapping
    public ResponseEntity<List<OrderRecord>> getAllCustomerOrdersWithStatus(
            @RequestParam Integer userId,
            @RequestParam OrderStatus status
    ) {
        List<OrderRecord> orders = orderRecordService.getAllCustomerOrdersWithStatus(userId, status);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @PostMapping(value = RESTAURANT_ID + CUSTOMER_ID)
    public ResponseEntity<OrderRecord> addOrderRecord(
            @PathVariable Long restaurantId,
            @PathVariable Long customerId,
            @Valid @RequestBody OrderDTO orderDTO
    ) {
        Customer customer = customerService.findCustomerById(customerId);

        OrderRecord savedOrderRecord = orderProcessingService
                .processAndCreateOrder(restaurantId, customer, orderDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderRecord);
    }

    @DeleteMapping(value = ORDER_RECORD_ID)
    public ResponseEntity<Void> deleteOrderRecord(
            @PathVariable Long orderRecordId
    ) {
        orderRecordService.deleteOrderWithPermission(orderRecordId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
