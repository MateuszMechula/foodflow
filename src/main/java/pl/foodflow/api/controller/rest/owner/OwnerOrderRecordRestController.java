package pl.foodflow.api.controller.rest.owner;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.foodflow.api.dto.OrderRecordDTO;
import pl.foodflow.api.dto.mapper.OrderRecordMapper;
import pl.foodflow.business.OrderRecordService;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.enums.OrderStatus;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = OwnerOrderRecordRestController.ORDER_RECORDS)
@Tag(name = "owner orderRecord")
public class OwnerOrderRecordRestController {

    public static final String ORDER_RECORDS = "/api/v1/owner/order-records";
    public static final String COMPLETE_ORDER = "/complete/{orderRecordId}";
    public static final String ORDER_RECORD_ID = "/{orderRecordId}";

    private final OrderRecordService orderRecordService;
    private final OrderRecordMapper orderRecordMapper;

    @Operation(summary = "Get orderRecord")
    @GetMapping(value = ORDER_RECORD_ID)
    public ResponseEntity<OrderRecordDTO> getOrderRecordById(@PathVariable Long orderRecordId) {
        OrderRecord orderRecord = orderRecordService.getOrderRecordById(orderRecordId);
        OrderRecordDTO orderRecordDTO = orderRecordMapper.mapToDTO(orderRecord);
        return ResponseEntity.status(HttpStatus.OK).body(orderRecordDTO);
    }

    @Operation(summary = "Get all owner orders with status")
    @GetMapping
    public ResponseEntity<List<OrderRecordDTO>> getAllOwnerOrdersWithStatus(
            @RequestParam Integer userId,
            @RequestParam OrderStatus status
    ) {
        List<OrderRecordDTO> orders = orderRecordService.getAllOwnerOrdersWithStatus(userId, status).stream()
                .map(orderRecordMapper::mapToDTO).toList();

        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @Operation(summary = "Complete order")
    @PostMapping(value = COMPLETE_ORDER)
    public ResponseEntity<Void> completeOrder(
            @PathVariable Long orderRecordId
    ) {
        log.info("Marking order as completed: {}", orderRecordId);
        orderRecordService.changeOrderStatusToCompleted(orderRecordId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
