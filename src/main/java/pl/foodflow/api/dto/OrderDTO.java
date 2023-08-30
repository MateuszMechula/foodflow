package pl.foodflow.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Map<Long, Integer> orderItems = new HashMap<>();
    private String orderNotes;
    private String contactPhone;
    private String deliveryAddress;
    private DeliveryDTO deliveryDTO;
}
