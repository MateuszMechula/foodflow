package pl.foodflow.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "addressId")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "street")
    private String street;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @OneToOne(mappedBy = "address")
    private OwnerEntity owner;

    @OneToOne(mappedBy = "address")
    private CustomerEntity customer;

    @OneToOne(mappedBy = "address")
    private RestaurantEntity restaurant;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "address")
    private Set<RestaurantAddressEntity> restaurantAddresses;
}
