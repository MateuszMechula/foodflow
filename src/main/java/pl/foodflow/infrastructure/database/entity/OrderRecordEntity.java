package pl.foodflow.infrastructure.database.entity;


import jakarta.persistence.*;
import lombok.*;
import pl.foodflow.infrastructure.database.converters.OffsetDateTimeAttributeConverter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "orderRecordId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_record")
public class OrderRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_record_id")
    private Long orderRecordId;

    @Column(name = "order_number", unique = true)
    private String orderNumber;

    @Convert(converter = OffsetDateTimeAttributeConverter.class)
    @Column(name = "order_date_time")
    private OffsetDateTime orderDateTime;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "order_notes")
    private String orderNotes;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "delivery_type")
    private String deliveryType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderRecord")
    private Set<OrderItemEntity> orderItems;
}
