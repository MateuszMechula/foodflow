package pl.foodflow.infrastructure.database.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.domain.RestaurantAddress;
import pl.foodflow.infrastructure.database.entity.RestaurantAddressEntity;
import pl.foodflow.infrastructure.database.repository.jpa.RestaurantAddressJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.RestaurantAddressMapper;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.foodflow.util.TestDataFactory.someRestaurantAddress1;
import static pl.foodflow.util.TestDataFactory.someRestaurantAddressEntity1;

@ExtendWith(MockitoExtension.class)
class RestaurantAddressRepositoryTest {
    @InjectMocks
    private RestaurantAddressRepository restaurantAddressRepository;
    @Mock
    private RestaurantAddressJpaRepository restaurantAddressJpaRepository;
    @Mock
    private RestaurantAddressMapper restaurantAddressMapper;

    @Test
    void shouldFindRestaurantAddressByAddressId() {
        //given
        Long addressId = 1L;
        RestaurantAddressEntity restaurantAddressEntity = someRestaurantAddressEntity1();
        RestaurantAddress restaurantAddress = someRestaurantAddress1();

        when(restaurantAddressJpaRepository.findRestaurantAddressEntityByAddressAddressId(addressId))
                .thenReturn(Optional.ofNullable(restaurantAddressEntity));
        when(restaurantAddressMapper.mapFromEntity(Objects.requireNonNull(restaurantAddressEntity)))
                .thenReturn(restaurantAddress);
        //when
        Optional<RestaurantAddress> foundRestaurantAddress = restaurantAddressRepository
                .findRestaurantAddressByAddressId(addressId);
        //then
        assertThat(foundRestaurantAddress)
                .isPresent()
                .isEqualTo(Optional.of(restaurantAddress));

        verify(restaurantAddressJpaRepository).findRestaurantAddressEntityByAddressAddressId(addressId);
        verify(restaurantAddressMapper).mapFromEntity(restaurantAddressEntity);
    }

    @Test
    void shouldSaveRestaurantAddress() {
        //given
        RestaurantAddress restaurantAddress = someRestaurantAddress1();
        RestaurantAddressEntity restaurantAddressEntity = someRestaurantAddressEntity1();

        when(restaurantAddressMapper.mapToEntity(restaurantAddress)).thenReturn(restaurantAddressEntity);
        when(restaurantAddressJpaRepository.save(restaurantAddressEntity)).thenReturn(restaurantAddressEntity);
        when(restaurantAddressMapper.mapFromEntity(restaurantAddressEntity)).thenReturn(restaurantAddress);
        //when
        RestaurantAddress foundRestaurantAddress = restaurantAddressRepository
                .saveRestaurantAddress(restaurantAddress);
        //then
        assertThat(foundRestaurantAddress)
                .isNotNull()
                .isEqualTo(restaurantAddress);
        verify(restaurantAddressMapper).mapToEntity(restaurantAddress);
        verify(restaurantAddressJpaRepository).save(restaurantAddressEntity);
        verify(restaurantAddressMapper).mapFromEntity(restaurantAddressEntity);
    }

    @Test
    void shouldDeleteRestaurantAddress() {
        //given
        RestaurantAddress restaurantAddress = someRestaurantAddress1();
        RestaurantAddressEntity restaurantAddressEntity = someRestaurantAddressEntity1();

        when(restaurantAddressMapper.mapToEntity(restaurantAddress)).thenReturn(someRestaurantAddressEntity1());
        //when
        restaurantAddressRepository.deleteRestaurantAddress(restaurantAddress);
        //then
        verify(restaurantAddressJpaRepository).delete(restaurantAddressEntity);
    }
}