package pl.foodflow.infrastructure.database.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.foodflow.domain.OrderRecord;
import pl.foodflow.infrastructure.database.entity.OrderRecordEntity;
import pl.foodflow.infrastructure.database.repository.jpa.OrderRecordJpaRepository;
import pl.foodflow.infrastructure.database.repository.mapper.OrderRecordEntityMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pl.foodflow.util.TestDataFactory.someOrderRecord1;
import static pl.foodflow.util.TestDataFactory.someOrderRecordEntity1;

@ExtendWith(MockitoExtension.class)
class OrderRecordRepositoryTest {
    @InjectMocks
    private OrderRecordRepository orderRecordRepository;
    @Mock
    private OrderRecordJpaRepository orderRecordJpaRepository;
    @Mock
    private OrderRecordEntityMapper orderRecordEntityMapper;

    @Test
    void shouldFindOrderRecordById() {
        //given
        Long orderRecordId = 1L;
        OrderRecordEntity orderRecordEntity = someOrderRecordEntity1();
        OrderRecord orderRecord = someOrderRecord1();

        when(orderRecordJpaRepository.findOrderRecordById(orderRecordId)).thenReturn(Optional.of(orderRecordEntity));
        when(orderRecordEntityMapper.mapFromEntity(orderRecordEntity)).thenReturn(orderRecord);
        //when
        Optional<OrderRecord> foundOrderRecord = orderRecordRepository.findOrderRecordById(orderRecordId);
        //then
        assertThat(foundOrderRecord)
                .isPresent()
                .contains(orderRecord);

        verify(orderRecordJpaRepository).findOrderRecordById(orderRecordId);
        verify(orderRecordEntityMapper).mapFromEntity(orderRecordEntity);
    }

    @Test
    void shouldFindAllOrderRecords() {
        //given
        var orderRecordEntities = List.of(someOrderRecordEntity1());
        var expectedOrderRecords = List.of(someOrderRecord1());

        when(orderRecordJpaRepository.findAll()).thenReturn(orderRecordEntities);
        when(orderRecordEntityMapper.mapFromEntity(any())).thenAnswer(invocation -> {
            OrderRecordEntity entity = invocation.getArgument(0);
            int index = orderRecordEntities.indexOf(entity);
            return expectedOrderRecords.get(index);
        });
        //when
        List<OrderRecord> orderRecords = orderRecordRepository.findAllOrderRecords();
        //then
        assertThat(orderRecords)
                .isNotNull()
                .hasSize(expectedOrderRecords.size());
        verify(orderRecordJpaRepository).findAll();
        verify(orderRecordEntityMapper, times(orderRecordEntities.size())).mapFromEntity(any());
    }

    @Test
    void shouldSaveOrderRecord() {
        //given
        OrderRecord orderRecord = someOrderRecord1();
        OrderRecordEntity orderRecordEntity = someOrderRecordEntity1();

        when(orderRecordEntityMapper.mapToEntity(orderRecord)).thenReturn(orderRecordEntity);
        when(orderRecordJpaRepository.save(orderRecordEntity)).thenReturn(orderRecordEntity);
        when(orderRecordEntityMapper.mapFromEntity(orderRecordEntity)).thenReturn(orderRecord);
        //when
        OrderRecord foundOrderRecord = orderRecordRepository.saveOrderRecord(orderRecord);
        //then
        Assertions.assertThat(foundOrderRecord)
                .isNotNull()
                .isEqualTo(orderRecord);

        verify(orderRecordEntityMapper).mapToEntity(orderRecord);
        verify(orderRecordJpaRepository).save(orderRecordEntity);
        verify(orderRecordEntityMapper).mapFromEntity(orderRecordEntity);
    }

    @Test
    void shouldDeleteOrderRecord() {
        //given
        OrderRecord orderRecord = someOrderRecord1();
        OrderRecordEntity orderRecordEntity = someOrderRecordEntity1();

        when(orderRecordEntityMapper.mapToEntity(orderRecord)).thenReturn(orderRecordEntity);
        //when
        orderRecordRepository.deleteOrderRecord(orderRecord);
        //then
        verify(orderRecordJpaRepository).delete(orderRecordEntity);
    }
}