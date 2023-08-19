package pl.foodflow.infrastructure.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetTime;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "nip")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "nip", length = 10, unique = true)
    @Pattern(regexp = "\\d{10}", message = "NIP must consist of 10 digits")
    private String nip;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "open_time")
    private OffsetTime openTime;

    @Column(name = "close_time")
    private OffsetTime closeTime;

    @Column(name = "phone")
    private String phone;

    @Column(name = "minimum_order_amount")
    private BigDecimal minimumOrderAmount;

    @Column(name = "delivery_price")
    private BigDecimal deliveryPrice;

    @Column(name = "delivery_option")
    private Boolean deliveryOption;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "restaurant")
    private MenuEntity menu;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<RestaurantAddressEntity> restaurantAddresses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<RestaurantCategoryEntity> restaurantCategories;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<OrderRecordEntity> orderRecords;
}
