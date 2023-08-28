package pl.foodflow.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.foodflow.domain.RestaurantAddress;
import pl.foodflow.infrastructure.database.entity.RestaurantAddressEntity;

@Mapper(uses = {AddressEntityMapper.class, RestaurantEntityMapper.class}, componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantAddressMapper {

    RestaurantAddressEntity mapToEntity(RestaurantAddress address);

    RestaurantAddress mapFromEntity(RestaurantAddressEntity saved);

//    default RestaurantAddressEntity mapToEntity2(RestaurantAddress saved) {
//        return RestaurantAddressEntity.builder()
//                .restaurant(RestaurantEntity.builder()
//                        .restaurantId(saved.getRestaurant().getRestaurantId())
//                        .restaurantAddresses(Set.of(RestaurantAddressEntity.builder()
//                                .restaurantAddressId(saved.getRestaurant().getRestaurantId())
//                                .build()))
//                        .build())
//                .address(AddressEntity.builder()
//                        .addressId(saved.getAddress().getAddressId())
//                        .street(saved.getAddress().getStreet())
//                        .city(saved.getAddress().getCity())
//                        .country(saved.getAddress().getCountry())
//                        .restaurantAddresses(Set.of(RestaurantAddressEntity.builder()
//                                .restaurantAddressId(saved.getRestaurantAddressId())
//                                .build()))
//                        .build())
//                .build();
//    }
//
//
//    default RestaurantAddress mapFromEntity2(RestaurantAddressEntity saved) {
//        return RestaurantAddress.builder()
//                .restaurantAddressId(saved.getRestaurantAddressId())
//                .restaurant(Restaurant.builder()
//                        .restaurantId(saved.getRestaurant().getRestaurantId())
//                        .restaurantAddresses(mapRestaurantAddresses(saved.getRestaurant().getRestaurantAddresses()))
//                        .build())
//                .address(Address.builder()
//                        .addressId(saved.getAddress().getAddressId())
//                        .street(saved.getAddress().getStreet())
//                        .city(saved.getAddress().getCity())
//                        .country(saved.getAddress().getCountry())
//                        .restaurantAddresses(mapRestaurantAddresses(saved.getRestaurant().getRestaurantAddresses()))
//                        .build())
//                .build();
//    }
//    default Set<RestaurantAddress> mapRestaurantAddresses(Set<RestaurantAddressEntity> restaurantAddressesEntities) {
//        if (restaurantAddressesEntities == null) {
//            return null;
//        }
//
//        return restaurantAddressesEntities.stream()
//                .map(this::mapRestaurantAddress)
//                .collect(Collectors.toSet());
//
//
//    }
//
//    default RestaurantAddress mapRestaurantAddress(RestaurantAddressEntity restaurantAddressEntity) {
//        if (restaurantAddressEntity == null) {
//            return null;
//        }
//
//        return RestaurantAddress.builder()
//                .restaurantAddressId(restaurantAddressEntity.getRestaurantAddressId())
//                .restaurant(Restaurant.builder()
//                        .restaurantId(restaurantAddressEntity.getRestaurant().getRestaurantId())
//                        .build())
//                .address(Address.builder()
//                        .addressId(restaurantAddressEntity.getAddress().getAddressId())
//                        .street(restaurantAddressEntity.getAddress().getStreet())
//                        .postalCode(restaurantAddressEntity.getAddress().getPostalCode())
//                        .city(restaurantAddressEntity.getAddress().getCity())
//                        .country(restaurantAddressEntity.getAddress().getCountry())
//                        .build())
//                .build();
//    }
//
//    private static Address mapRestaurantAddresses(RestaurantEntity saved) {
//        return Address.builder()
//                .addressId(saved.getAddress().getAddressId())
//                .street(saved.getAddress().getStreet())
//                .city(saved.getAddress().getCity())
//                .postalCode(saved.getAddress().getPostalCode())
//                .country(saved.getAddress().getCountry())
//                .build();
//    }
//

}
