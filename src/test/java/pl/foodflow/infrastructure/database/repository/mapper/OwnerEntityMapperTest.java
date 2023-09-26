package pl.foodflow.infrastructure.database.repository.mapper;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.foodflow.domain.Owner;
import pl.foodflow.infrastructure.database.entity.OwnerEntity;
import pl.foodflow.util.TestDataForMappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OwnerEntityMapperTest {
    private OwnerEntityMapper ownerEntityMapper;

    @Test
    void shouldMapOwnerToOwnerEntity() {
        //given
        Owner owner = TestDataForMappers.someOwner();
        //when
        OwnerEntity entity = ownerEntityMapper.mapToEntity(owner);
        //then
        assertEquals(owner.getName(), entity.getName());
        assertEquals(owner.getSurname(), entity.getSurname());
        assertEquals(owner.getEmail(), entity.getEmail());
    }

    @Test
    void shouldMapOwnerEntityToOwner() {
        //given
        OwnerEntity ownerEntity = TestDataForMappers.someOwnerEntity();
        //when
        Owner owner = ownerEntityMapper.mapFromEntity(ownerEntity);
        //then
        assertEquals(ownerEntity.getName(), owner.getName());
        assertEquals(ownerEntity.getSurname(), owner.getSurname());
        assertEquals(ownerEntity.getEmail(), owner.getEmail());
    }
}