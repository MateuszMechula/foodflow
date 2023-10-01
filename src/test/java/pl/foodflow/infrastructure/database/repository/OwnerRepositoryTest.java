package pl.foodflow.infrastructure.database.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.database.entity.OwnerEntity;
import pl.foodflow.infrastructure.database.repository.jpa.OwnerJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.OwnerEntityMapper;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.foodflow.util.TestDataFactory.someOwner1;
import static pl.foodflow.util.TestDataFactory.someOwnerEntity1;

@ExtendWith(MockitoExtension.class)
class OwnerRepositoryTest {
    @InjectMocks
    private OwnerRepository ownerRepository;
    @Mock
    private OwnerJpaRepository ownerJpaRepository;
    @Mock
    private OwnerEntityMapper ownerEntityMapper;

    @Test
    void shouldFindOwnerByUserId() {
        //given
        Integer userId = 1;
        Owner owner = someOwner1();
        OwnerEntity ownerEntity = someOwnerEntity1();

        when(ownerJpaRepository.findOwnerEntityByUserId(userId)).thenReturn(Optional.ofNullable(ownerEntity));
        when(ownerEntityMapper.mapFromEntity(ownerEntity)).thenReturn(owner);
        //when
        Optional<Owner> foundOwner = ownerRepository.findOwnerByUserId(userId);
        //then
        Assertions.assertThat(foundOwner)
                .isPresent()
                .isEqualTo(Optional.of(owner));
        verify(ownerJpaRepository).findOwnerEntityByUserId(userId);
        verify(ownerEntityMapper).mapFromEntity(ownerEntity);
    }

    @Test
    void shouldSaveOwner() {
        //given
        Owner owner = someOwner1();
        OwnerEntity ownerEntity = someOwnerEntity1();

        when(ownerEntityMapper.mapToEntity(owner)).thenReturn(ownerEntity);
        //when
        ownerRepository.saveOwner(owner);
        //then
        verify(ownerEntityMapper).mapToEntity(owner);
        verify(ownerJpaRepository).save(ownerEntity);
    }
}