package pl.foodflow.infrastructure.database.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.domain.Menu;
import pl.foodflow.infrastructure.database.entity.MenuEntity;
import pl.foodflow.infrastructure.database.repository.jpa.MenuJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.MenuEntityMapper;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.foodflow.util.TestDataFactory.someMenu1;
import static pl.foodflow.util.TestDataFactory.someMenuEntity1;

@ExtendWith(MockitoExtension.class)
class MenuRepositoryTest {
    @InjectMocks
    private MenuRepository menuRepository;
    @Mock
    private MenuJpaRepository menuJpaRepository;
    @Mock
    private MenuEntityMapper menuEntityMapper;

    @Test
    void shouldFindMenuById() {
        //given
        Long menuId = 1L;
        Menu menu = someMenu1();
        MenuEntity menuEntity = someMenuEntity1();

        when(menuJpaRepository.findById(menuId)).thenReturn(Optional.of(menuEntity));
        when(menuEntityMapper.mapFromEntity(menuEntity)).thenReturn(menu);
        //when
        Optional<Menu> foundMenu = menuRepository.findMenuById(menuId);
        //then
        Assertions.assertThat(foundMenu)
                .isPresent()
                .contains(menu);
        verify(menuJpaRepository).findById(menuId);
        verify(menuEntityMapper).mapFromEntity(menuEntity);
    }

    @Test
    void shouldSaveMenu() {
        //given
        Menu menu = someMenu1();
        MenuEntity menuEntity = someMenuEntity1();

        when(menuEntityMapper.mapToEntity(someMenu1())).thenReturn(menuEntity);
        when(menuJpaRepository.saveAndFlush(menuEntity)).thenReturn(menuEntity);
        when(menuEntityMapper.mapFromEntity(menuEntity)).thenReturn(menu);
        //when
        menuRepository.saveMenu(menu);
        //then
        verify(menuEntityMapper).mapToEntity(menu);
        verify(menuJpaRepository).saveAndFlush(menuEntity);
        verify(menuEntityMapper).mapFromEntity(menuEntity);
    }

    @Test
    void shouldDeleteMenuById() {
        //given
        Long menuId = 1L;

        //when
        menuRepository.deleteMenuById(menuId);
        //then
        verify(menuJpaRepository).deleteById(menuId);
    }
}