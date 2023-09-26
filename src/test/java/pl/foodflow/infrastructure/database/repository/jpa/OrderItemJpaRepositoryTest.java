package pl.foodflow.infrastructure.database.repository.jpa;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import pl.foodflow.infrastructure.database.entity.OrderItemEntity;
import pl.foodflow.infrastructure.database.entity.OrderRecordEntity;
import pl.foodflow.infrastructure.security.user.UserJpaRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.foodflow.util.TestDataFactory.*;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@TestPropertySource(properties = "test.name=OrderItemJpaRepositoryTest")
class OrderItemJpaRepositoryTest extends AbstractJpa {

    private UserJpaRepository userJpaRepository;
    private MenuJpaRepository menuJpaRepository;
    private OwnerJpaRepository ownerJpaRepository;
    private CustomerJpaRepository customerJpaRepository;
    private OrderItemJpaRepository orderItemJpaRepository;
    private RestaurantJpaRepository restaurantJpaRepository;
    private OrderRecordJpaRepository orderRecordJpaRepository;
    private CategoryItemJpaRepository categoryItemJpaRepository;
    private MenuCategoryJpaRepository menuCategoryJpaRepository;

    @Test
    @Transactional
    void shouldDeleteOrderItemByOrderRecordId() {
        //given
        userJpaRepository.saveAndFlush(someUserEntity1());
        ownerJpaRepository.saveAndFlush(someOwnerEntity1());
        customerJpaRepository.saveAndFlush(someCustomerEntity1());
        restaurantJpaRepository.saveAndFlush(someRestaurantEntity1());
        menuJpaRepository.saveAndFlush(someMenuEntity1());
        menuCategoryJpaRepository.saveAndFlush(someMenuCategoryEntity1());
        categoryItemJpaRepository.saveAndFlush(someCategoryItemEntity1());
        OrderRecordEntity orderRecord = someOrderRecordEntity1();
        orderRecordJpaRepository.saveAndFlush(orderRecord);

        OrderItemEntity orderItem = someOrderItemEntity1();
        orderItemJpaRepository.saveAndFlush(orderItem);

        //when
        Optional<OrderItemEntity> expected = orderItemJpaRepository.findById(orderItem.getOrderItemId());
        //then
        assertThat(expected).isPresent();
        orderItemJpaRepository.deleteByOrderRecord(orderRecord);
        Optional<OrderItemEntity> deleted = orderItemJpaRepository.findById(orderItem.getOrderItemId());
        assertThat(deleted).isEmpty();
    }
}