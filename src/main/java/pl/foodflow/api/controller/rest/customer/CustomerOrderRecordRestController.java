package pl.foodflow.api.controller.rest.customer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.foodflow.api.dto.OrderDTO;
import pl.foodflow.api.dto.OrderRecordDTO;
import pl.foodflow.api.dto.mapper.OrderRecordMapper;
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
@Tag(name = "customer orderRecord")
public class CustomerOrderRecordRestController {
    public static final String ORDER_RECORDS = "/api/v1/customer/order-records";
    public static final String RESTAURANT_ID = "/{restaurantId}";
    public static final String CUSTOMER_ID = "/{customerId}";
    public static final String ORDER_RECORD_ID = "/{orderRecordId}";

    private final CustomerService customerService;
    private final OrderRecordService orderRecordService;
    private final OrderProcessingService orderProcessingService;
    private final OrderRecordMapper orderRecordMapper;

    @Operation(summary = "Get all customer orders with status")
    @GetMapping
    public ResponseEntity<List<OrderRecordDTO>> getAllCustomerOrdersWithStatus(
            @RequestParam Integer userId,
            @RequestParam OrderStatus status
    ) {
        List<OrderRecordDTO> orders = orderRecordService.getAllCustomerOrdersWithStatus(userId, status)
                .stream().map(orderRecordMapper::mapToDTO).toList();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @Operation(
            summary = "Add order record",
            description = "Create a new order record with the following data structure:\n"
                    + "```json\n"
                    + "{\n"
                    + "  \"orderItems\": {\n"
                    + "    \"categoryItemId1\": 3,  // 'categoryItemId' with 'quantity'\n"
                    + "    \"categoryItemId2\": 2,  // 'categoryItemId' with 'quantity'\n"
                    + "    ...\n"
                    + "  },\n"
                    + "  \"orderNotes\": \"string\",\n"
                    + "  \"contactPhone\": \"867996949\",\n"
                    + "  \"deliveryAddress\": \"string\",\n"
                    + "  \"deliveryType\": \"DELIVERY\"\n"
                    + "}\n"
    )
    @PostMapping(value = RESTAURANT_ID + CUSTOMER_ID)
    public ResponseEntity<OrderRecord> addOrderRecord(
            @PathVariable Long restaurantId,
            @PathVariable Long customerId,
            @Valid @RequestBody OrderDTO orderDTO
    ) {
        Customer customer = customerService.findCustomerById(customerId);

        OrderRecord orderRecord = orderProcessingService
                .processAndCreateOrder(restaurantId, customer, orderDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderRecord);
    }

    @Operation(summary = "Delete order record within 5 minutes of placing the order")
    @DeleteMapping(value = ORDER_RECORD_ID)
    public ResponseEntity<Void> deleteOrderRecord(
            @PathVariable Long orderRecordId
    ) {
        orderRecordService.deleteOrderWithPermission(orderRecordId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
