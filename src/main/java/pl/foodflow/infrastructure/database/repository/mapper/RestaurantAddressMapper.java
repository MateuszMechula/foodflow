package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.Address;
import pl.foodflow.domain.Restaurant;
import pl.foodflow.domain.RestaurantAddress;
import pl.foodflow.infrastructure.database.entity.RestaurantAddressEntity;
import pl.foodflow.infrastructure.database.entity.RestaurantEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantAddressMapper {

    RestaurantAddressEntity mapToEntity(RestaurantAddress address);
//    @Mappings({
//            @Mapping(target = "restaurant.owner", ignore = true),
//            @Mapping(target = "restaurant.menu", ignore = true),
//            @Mapping(target = "restaurant.address", ignore = true),
//            @Mapping(target = "restaurant.restaurantAddresses", ignore = true),
//            @Mapping(target = "restaurant.restaurantCategories", ignore = true),
//            @Mapping(target = "restaurant.orderRecords", ignore = true),
//
//            @Mapping(target = "address.owner", ignore = true),
//            @Mapping(target = "address.customer", ignore = true),
//            @Mapping(target = "address.restaurant", ignore = true),
//            @Mapping(target = "address.restaurantAddresses", ignore = true)
//    })
//    RestaurantAddress mapFromEntity2(RestaurantAddressEntity saved);


    default RestaurantAddress mapFromEntity(RestaurantAddressEntity restaurantAddressEntity){
            return RestaurantAddress.builder()
                    .restaurantAddressId(restaurantAddressEntity.getRestaurantAddressId())
                    .restaurant(Restaurant.builder()
                            .restaurantId(restaurantAddressEntity.getRestaurant().getRestaurantId())
                            .nip(restaurantAddressEntity.getRestaurant().getNip())
                            .name(restaurantAddressEntity.getRestaurant().getName())
                            .description(restaurantAddressEntity.getRestaurant().getDescription())
                            .openTime(restaurantAddressEntity.getRestaurant().getOpenTime())
                            .closeTime(restaurantAddressEntity.getRestaurant().getCloseTime())
                            .phone(restaurantAddressEntity.getRestaurant().getPhone())
                            .minimumOrderAmount(restaurantAddressEntity.getRestaurant().getMinimumOrderAmount())
                            .deliveryPrice(restaurantAddressEntity.getRestaurant().getDeliveryPrice())
                            .restaurantAddresses(mapRestaurantAddresses(restaurantAddressEntity.getRestaurant().getRestaurantAddresses()))
                            .build())
                    .address(Address.builder()
                            .addressId(restaurantAddressEntity.getAddress().getAddressId())
                            .street(restaurantAddressEntity.getAddress().getStreet())
                            .city(restaurantAddressEntity.getAddress().getCity())
                            .postalCode(restaurantAddressEntity.getAddress().getPostalCode())
                            .country(restaurantAddressEntity.getAddress().getCountry())
                            .restaurantAddresses(mapRestaurantAddresses(restaurantAddressEntity.getAddress().getRestaurantAddresses()))
                            .build())
                    .build();
    }

    default Set<RestaurantAddress> mapRestaurantAddresses(Set<RestaurantAddressEntity> restaurantAddressesEntities) {
        if (restaurantAddressesEntities == null) {
            return null;
        }

        return restaurantAddressesEntities.stream()
                .map(this::mapRestaurantAddress)
                .collect(Collectors.toSet());


    }

    default RestaurantAddress mapRestaurantAddress(RestaurantAddressEntity restaurantAddressEntity) {
        if (restaurantAddressEntity == null) {
            return null;
        }

        return RestaurantAddress.builder()
                .restaurantAddressId(restaurantAddressEntity.getRestaurantAddressId())
                .restaurant(Restaurant.builder()
                        .restaurantId(restaurantAddressEntity.getRestaurant().getRestaurantId())
                        .build())
                .address(Address.builder()
                        .addressId(restaurantAddressEntity.getAddress().getAddressId())
                        .street(restaurantAddressEntity.getAddress().getStreet())
                        .postalCode(restaurantAddressEntity.getAddress().getPostalCode())
                        .city(restaurantAddressEntity.getAddress().getCity())
                        .country(restaurantAddressEntity.getAddress().getCountry())
                        .build())
                .build();
    }


}
