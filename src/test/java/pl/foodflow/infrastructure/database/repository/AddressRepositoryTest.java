package pl.foodflow.infrastructure.database.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.domain.Address;
import pl.foodflow.infrastructure.database.entity.AddressEntity;
import pl.foodflow.infrastructure.database.repository.jpa.AddressJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.AddressEntityMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.foodflow.util.TestDataFactory.someAddress1;
import static pl.foodflow.util.TestDataFactory.someAddressEntity1;

@ExtendWith(MockitoExtension.class)
class AddressRepositoryTest {
    @InjectMocks
    private AddressRepository addressRepository;
    @Mock
    private AddressEntityMapper addressEntityMapper;
    @Mock
    private AddressJpaRepository addressJpaRepository;

    @Test
    void shouldSaveAddress() {
        //given
        Address address = someAddress1();
        AddressEntity addressEntity = someAddressEntity1();

        when(addressEntityMapper.mapToEntity(address)).thenReturn(addressEntity);
        when(addressJpaRepository.save(addressEntity)).thenReturn(addressEntity);
        when(addressEntityMapper.mapFromEntity(addressEntity)).thenReturn(address);
        //when
        Address savedAddress = addressRepository.saveAddress(address);
        //then
        assertNotNull(savedAddress);
        verify(addressEntityMapper).mapToEntity(address);
        verify(addressJpaRepository).save(addressEntity);
        verify(addressEntityMapper).mapFromEntity(addressEntity);
    }

    @Test
    void shouldFindAddressById() {
        //given
        Long addressId = 1L;
        AddressEntity addressEntity = someAddressEntity1();
        Address address = someAddress1();

        when(addressJpaRepository.findById(addressId)).thenReturn(Optional.of(addressEntity));
        when(addressEntityMapper.mapFromEntity(addressEntity)).thenReturn(address);
        //when
        Optional<Address> foundAddress = addressRepository.findById(addressId);
        //then
        assertTrue(foundAddress.isPresent());
        assertEquals(address, foundAddress.get());
        verify(addressJpaRepository).findById(addressId);
        verify(addressEntityMapper).mapFromEntity(addressEntity);
    }
}