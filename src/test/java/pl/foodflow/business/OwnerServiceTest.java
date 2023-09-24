package pl.foodflow.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.business.dao.OwnerDAO;
import pl.foodflow.business.exceptions.OwnerNotFoundException;
import pl.foodflow.domain.Owner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.foodflow.util.TestDataFactory.someOwner1;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {
    @InjectMocks
    private OwnerService ownerService;
    @Mock
    private OwnerDAO ownerDAO;

    @Test
    void shouldFindOwnerByUserId() {
        //given
        Integer userId = 1;
        Owner owner = someOwner1();
        when(ownerDAO.findOwnerByUserId(userId)).thenReturn(Optional.ofNullable(owner));
        //when
        Owner expected = ownerService.findOwnerByUserId(userId);
        //then
        assertNotNull(expected);
        assertEquals(expected, owner);
    }

    @Test
    void shouldThrowExceptionWhenOwnerUserIdDoesNotExist() {
        //given
        Integer userId = 1;
        when(ownerDAO.findOwnerByUserId(userId)).thenReturn(Optional.empty());
        //when,then
        assertThrows(OwnerNotFoundException.class, () -> ownerService.findOwnerByUserId(userId));
        verify(ownerDAO).findOwnerByUserId(userId);
    }

    @Test
    void shouldSaveOwner() {
        //given
        Owner owner = someOwner1();
        //when
        ownerService.saveOwner(owner);
        //then
        verify(ownerDAO).saveOwner(owner);
    }
}